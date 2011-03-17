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
        
        localSvnRootFolder = os.path.join(config.workspace, "SVN_ROOT")
        
        if os.path.isdir(localSvnRootFolder) == True:
            self.updateSvnTree(config, localSvnRootFolder)
        else:
            self.checkoutSvnTree(config, localSvnRootFolder)
        
        
        preConditions = PreconditionChecker()
        if PRECONDITIONS_ENABLED == True:
            if preConditions.areSatisfied(config) == False:
                loge("There were unsatisfied preconditions!")
                return
        
        if SYSEXEC_ENABLED == True:
            print
            print "Configured artifacts:"
            for i, artifact in enumerate(config.artifacts):
                print "  %i. %s - Release Version: %s, Next Version: %s" % ( (i+1), artifact.artifactId, artifact.versionRelease, artifact.versionNext )
            print
            print "Are you really sure? SYSEXEC is enabled!"
            if inputConfirmation(True) == False:
                return False
        
        artifact = None
        try:
            for i, artifact in enumerate(config.artifacts):
                logi("---------------------- Releasing artifact '%s' with version %s (%i of %i)" %
                     (artifact.artifactId, artifact.versionRelease, i+1, len(config.artifacts)))
                if config.sayEnabled: sayOsx("releasing artifact %s" % artifact.artifactId)
                
                currentArtifactFolder = "%s/%s/trunk" % (localSvnRootFolder, artifact.svnRelativeToArtifactsBase)
                chdir(currentArtifactFolder)
                self.mvnRelease(artifact, config)
#                self.validatePostconditions(artifact)
                
                logi("---------------------- Finished releasing '%s'" % artifact.artifactId)
            logi("RELEASE SUCCESS")
        except Exception as e:
            loge("Error while releasing artifact %s: %s" % (artifact, e.message), e)
            return False
        
        try:
            self.processReactor(config, localSvnRootFolder)
        except Exception as e:
            loge("Error while processing reactor %s: %s" % (config.reactor, e.message), e)
            return False
        
        return True
    
    def processReactor(self, config, localSvnRootFolder):
        if config.reactor == None:
            return
        reactor = config.reactor
        logi("Processing reactor: %s" % reactor)
        
        artifactIdAndVersion = "%s-%s" % (reactor.artifactId, reactor.versionRelease)
        
        svnReactorBasePath = "%s/%s" % (config.svnArtifactsRoot, reactor.svnRelativeToArtifactsBase)
        svnTrunkPath = "%s/trunk" % svnReactorBasePath
        svnTagPath = "%s/tags/%s" % (svnReactorBasePath, artifactIdAndVersion)
        svnCommitMsg = "[release-app] Tagging reactor project: %s" % artifactIdAndVersion
        
        print "Going to create a tag of the reactor project."
        print "Please GET SURE the POM in trunk has proper (fixed) VERSION NUMBERS!!!"
        if inputConfirmation() == False:
            logd("Aborted reactor processing.")
            return
        
        # LUXURY in here we could check if submodules, referenced in reactor project, are really existing (tags where created for specific modules)
        svn("copy %s %s -m \"%s\" --username %s --password %s" % (svnTrunkPath, svnTagPath, svnCommitMsg, config.username, config.password))
        
        self.updateSvnTree(config, localSvnRootFolder)
        
        reactorLocalSvnTagPath = "%s/tags/%s" % (os.path.join(localSvnRootFolder, reactor.svnRelativeToArtifactsBase), artifactIdAndVersion)
        chdir(reactorLocalSvnTagPath)
        
        mvn("clean site")
        mvn("dashboard:dashboard")
        mvn("deploy site:deploy")
        
        logi("Confirm there are no local changes (can not do it automatically):")
        svn("status")
        
        print
        print "Please increment version numbers (and update module paths) of reactor project."
        hitEnter()
    
    def validatePostconditions(self, artifact):
        logd("Checking post-conditions ...")
        failedPostCond = PostconditionChecker().lookupErrorsForArtifact(artifact)
        if len(failedPostCond) == 0:
            return
        raise Exception("Failed postconditions for artifact %s!\n%s" % (artifact, "\n".join(failedPostCond)))
    
    
    def mvnRelease(self, artifact, config):
        releasePluginArgs = " ".join([
             # common release plugin options (http://maven.apache.org/plugins/maven-release-plugin/prepare-mojo.html)
             "-Dusername=%s" % config.username,
             "-Dpassword=%s" % config.password,
             ("-DscmCommentPrefix=\"[release-app] releasing verison '%s' of artifact '%s'\"" %
              (artifact.versionRelease, artifact.artifactId)),
             # release:prepare specific options
             "-DallowTimestampedSnapshots=false", # default=false
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
        
        self.mvnExecuteReleaseSafe("clean release:prepare " + releasePluginArgs)
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
            finally:
                raise e
    
    def updateSvnTree(self, config, targetPath):
        svn("update %s --username %s --password %s" % (targetPath, config.username, config.password))
        
    def checkoutSvnTree(self, config, targetPath):
        svn("checkout %s %s --username %s --password %s" % (config.svnArtifactsRoot, targetPath, config.username, config.password))
        
#        logd("Checking out artifact: %s" % artifact.artifactId)
#        
#        targetPath = "%s-%s-SNAPSHOT" % (os.path.join(targetFolder, artifact.artifactId), artifact.versionRelease)
#        svnCheckoutPath = "%s/%s/trunk" % (config.svnRoot, artifact.svnRelativeToArtifactsBase)
#        svn("checkout %s %s --username %s --password %s" % (svnCheckoutPath, targetPath, config.username, config.password))
#
#        return targetPath

# PYTHON CHEAT SHEET
#
# os.path.exists/isfile/isabs("bob.txt")
# os.getcwd()
# os.system("mvn clean verify")
# os.system("mvn -U -up -B ... clean release:prepare")
#from subprocess import call
    #call(["say", text])