from config_global import * #@UnusedWildImport
from commons import * #@UnusedWildImport
from logging import * #@UnusedWildImport
from sysexec import * #@UnusedWildImport
from PreconditionChecker import PreconditionChecker
from PostconditionChecker import PostconditionChecker

#* redirect output from process

# post tasks
# =================
# check existence of artifacts via web (documentation/)
# sourceforge deploy File
# sourceforge README (optional nochmal als grosses ding dazu (nicht per projekt)
        
class Releaser:
    
    def createNewWith(self, config):
        
        # TODO validate if mvn2 is active (not mvn3, as wagon SCP provider does not work)
        # TODO confirm artifacts configuration by user!!!
        
        if os.path.isdir(config.workspace) == 0 and os.path.isabs(config.workspace):
            raise Exception("Invalid workspace directory (please create it): %s" % config.workspace)
        
        localSvnRootFolder = config.localSvnRoot
        
        if os.path.isdir(localSvnRootFolder) == False:
            raise Exception("Please checkout the whole artifact SVN tree to: %s" % localSvnRootFolder)
        
        preConditions = PreconditionChecker()
        if PRECONDITIONS_ENABLED == True:
            if preConditions.areSatisfied(config) == False:
                loge("There were unsatisfied preconditions!")
                return
        
        if SYSEXEC_ENABLED == True:
            print
            if len(config.artifacts) == 0:
                print "No artifacts are configured at all"
            else:
                print "Configured artifacts:"
                for i, artifact in enumerate(config.artifacts):
                    print "  %i. %s - Release / Next Version: %s / %s, SVN Path: %s" % ( (i+1), 
                                                                                             artifact.artifactId,
                                                                                             artifact.versionRelease,
                                                                                             artifact.versionNext,
                                                                                             artifact.svnRelativeToRoot )
            print
            print "Are you really sure? SYSEXEC is enabled!"
            if inputConfirmation(True) == False:
                return False
        
        self.updateSvnTree(config, localSvnRootFolder)
        
        # TODO hier noch kompletten testrun machen: mvn verify site fuer alle + reaktor machen, bevor mvn release machen
        
        artifact = None
        try:
            for i, artifact in enumerate(config.artifacts):
                logi("---------------------- Releasing artifact '%s' with version %s (%i of %i)" %
                     (artifact.artifactId, artifact.versionRelease, i+1, len(config.artifacts)))
                
                notifyi("Processing ...", "Artifact '%s-%s'" % (artifact.artifactId, artifact.versionRelease))
                if config.sayEnabled: sayOsx("releasing artifact %s" % artifact.artifactId)
                
                currentArtifactFolder = "%s/%s/trunk" % (localSvnRootFolder, artifact.svnRelativeToLocalArtifact)
                chdir(currentArtifactFolder)
                self.mvnRelease(config, artifact)
                self.validatePostconditions(config, artifact)
                
                logi("---------------------- Finished releasing '%s'" % artifact.artifactId)
            logi("RELEASE SUCCESS")
        except Exception as e:
            loge("Error while releasing artifact %s: %s" % (artifact, e.message), e)
            loge("If this happened during a mvn release:perform, please get sure to delete the Subversion Tag.") # TODO move this inside the loop (to get exact svn tag path)
            return False
        
        try:
            self.processReactor(config, localSvnRootFolder)
        except Exception as e:
            loge("Error while processing reactor %s: %s" % (config.reactor, e.message), e)
            return False
        
        return True
    
    def processReactor(self, config, localSvnRootFolder):
        if config.reactor == None:
            logi("No reactor to process defined; skipping.")
            return
        
        reactor = config.reactor
        logi("Processing reactor: %s" % reactor)
        notifyi("Processing ...", "Reactor '%s-%s'" % (reactor.artifactId, reactor.versionRelease))
        
        artifactIdAndVersion = "%s-%s" % (reactor.artifactId, reactor.versionRelease)
        
        # we need write access to svn via https as we are going to create a tag
        httpsifiedUrlSvnWebRoot = config.urlSvnWebRoot.replace("http:", "https:")
        svnReactorBasePath = "%s/%s" % (httpsifiedUrlSvnWebRoot, reactor.svnRelativeToRoot)
        svnTrunkPath = "%s/trunk" % svnReactorBasePath
        svnTagPath = "%s/tags/%s" % (svnReactorBasePath, artifactIdAndVersion)
        svnCommitMsg = "[release-app] svn tagging reactor '%s'" % artifactIdAndVersion
        
        print "Going to create a Subversion Tag of Reactor Project '%s'." % artifactIdAndVersion
        print
        print "Please CONFIRM the trunk POM has properly fixed VERSION NUMBERS!"
        print "   // it's own version and the submodules should reference ' ../../../foo/tags/foo-1.x/'"
        reactorPomXmlFileWebUrl = "%s/pom.xml" % (svnTrunkPath)
        print "  POM which should be checked: %s" % reactorPomXmlFileWebUrl
        if inputConfirmation() == False:
            logd("Aborted reactor processing.")
            return
        
        # LUXURY in here we could check if submodules, referenced in reactor project, are really existing (tags where created for specific modules)
        notifyt("svn copy", "Creating Tag: '%s'" % svnTagPath)
        svn("copy %s %s -m \"%s\" --username %s --password %s" % (svnTrunkPath, svnTagPath, svnCommitMsg, config.username, config.password))
        
        self.updateSvnTree(config, localSvnRootFolder)
        
        reactorLocalSvnTagPath = "%s/tags/%s" % (os.path.join(localSvnRootFolder, reactor.svnRelativeToLocalArtifact), artifactIdAndVersion)
        chdir(reactorLocalSvnTagPath)
        
        notifyt("Site Reactor", "mvn clean site")
        mvn("clean site") # FIXME this will build all reports for all modules AGAIN! any solution? is it really necessary?
        notifyt("Site Reactor", "mvn dashboard:dashboard")
        mvn("dashboard:dashboard")
        notifyt("Site Reactor", "deploy site:deploy")
        mvn("deploy site:deploy") # FIXME this will redeploy all tagged artifacts a second time ==> disable submodules!
        
        logi("Confirm there are no local changes (can not do it automatically):")
        svn("status")
        
        print
        print "Finished creating Site Reactor."
        print "Please update version numbers and update module paths in its pom.xml."
        hitEnter()
    
    def validatePostconditions(self, config, artifact):
        logd("Checking post-conditions ...")
        notifyd("Post Conditions", "Checking postconditions for %s" % artifact.artifactId)
        failedPostCond = PostconditionChecker().lookupErrorsForArtifact(config, artifact)
        if len(failedPostCond) != 0:
            raise Exception("Failed postconditions for artifact %s!\n%s" % (artifact, "\n".join(failedPostCond)))
    
    def mvnRelease(self, config, artifact):
        releasePluginArgs = " ".join([
             # common release plugin options (http://maven.apache.org/plugins/maven-release-plugin/prepare-mojo.html)
             "-Dusername=%s" % config.username,
             "-Dpassword=%s" % config.password,
             ("-DscmCommentPrefix=\"[release-app] mvn releasing artifact '%s-%s'\"" %
              (artifact.artifactId, artifact.versionRelease)),
             # release:prepare specific options
             "-DallowTimestampedSnapshots=false", # default=false
             "-DignoreSnapshots=false", # undocumented property (allowTimestampedSnapshots is documented, but does not work!)
             "-DreleaseVersion=%s" % artifact.versionRelease,
             "-DdevelopmentVersion=%s" % artifact.versionNext,
             "-DupdateDependencies=false", # default=true
#             "--DpreparationGoals=clean verify", # default="clean verify"
#                    <autoVersionSubmodules>false</autoVersionSubmodules><!-- default: "false" -->
#                    <!-- checkModificationExcludeList -->
             # release:perform specific options
#             "--DreleaseProfiles=aProfile,bProfile",
#             "--Dgoals=xxx", # default = "deploy" or "deploy site-deploy"
             # maven 2 specific options
             "--batch-mode", # Run in non-interactive (batch) mode
             "--update-plugins", # Force upToDate check for any relevant registered plugins (same as "--check-plugin-updates")
             "--update-snapshots", # Forces a check for updated releases and snapshots on remote repositories
             "--show-version", # Display version information WITHOUT stopping build
                # --debug # Produce execution debug output
                # --resume-from <arg> # Resume reactor from specified artifact
                # --quiet # Quiet output - only show errors
             ])
        
        notifyt("Preparing '%s'" % artifact.artifactId, "mvn clean release:prepare")
        self.mvnExecuteReleaseSafe("clean release:prepare " + releasePluginArgs)

        notifyt("Releasing '%s'" % artifact.artifactId, "mvn release:perform")        
        self.mvnExecuteReleaseSafe("release:perform " + releasePluginArgs)
    
    def mvnExecuteReleaseSafe(self, args):
        try:
            mvn(args)
        except Exception as e:
            loge("Could not perform release! Rolling back ... (is SSH shell really open?!)", e)
            try:
                mvn("release:rollback")
            except Exception as e2:
                loge("Rolling back from failed release:perform failed! Ouch...", e2)
                notifye("Maven Rollback Failed", "This should really not have happened :(")
            finally:
                raise e
    
    def updateSvnTree(self, config, targetPath):
        svn("update %s --set-depth infinity --force --username %s --password %s" % (targetPath, config.username, config.password))
        
# PYTHON CHEAT SHEET
#
# os.path.exists/isfile/isabs("bob.txt")
# os.getcwd()
# os.system("mvn clean verify")
# os.system("mvn -U -up -B ... clean release:prepare")
#from subprocess import call
    #call(["say", text])