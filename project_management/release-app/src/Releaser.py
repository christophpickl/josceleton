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
        
        targetFolder = prepareTargetFolder(config.workspace)
        
        preConditions = PreconditionChecker()
        if PRECONDITIONS_ENABLED == True:
            if preConditions.areSatisfied(config) == False:
                loge("There were unsatisfied preconditions!")
                return
        
        if SYSEXEC_ENABLED == True:
            print
            raw_input("Are you really sure? sysexec is enabled!")
            print
        
        for i, artifact in enumerate(config.artifacts):
            try:
                logi("---------------------- Releasing artifact '%s' with version %s (%i of %i)" %
                     (artifact.artifactId, artifact.versionRelease, i+1, len(config.artifacts)))
                if config.sayEnabled: sayOsx("releasing artifact %s" % artifact.artifactId)
                
                self.processArtifact(artifact, config, targetFolder)
                self.validatePostconditions(artifact)
                
                logi("---------------------- Finished releasing '%s'" % artifact.artifactId)
                
            except Exception as e:
                loge("Error while releasing artifact %s: %s" % (artifact, e.message), e)
                break
        
        logi("RELEASE SUCCESS")
    
    def validatePostconditions(self, artifact):
        logd("Checking post-conditions ...")
        failedPostCond = PostconditionChecker().lookupErrorsForArtifact(artifact)
        if len(failedPostCond) == 0:
            return
        raise Exception("Failed postconditions for artifact %s!\n%s" % (artifact, "\n".join(failedPostCond)))
    
    
    def processArtifact(self, artifact, config, targetFolder):
        checkoutFolder = self.checkout(artifact, config, targetFolder)
        
        chdir(checkoutFolder)
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
        mvn("clean release:prepare " + releasePluginArgs)
        
        try:
            mvn("release:perform " + releasePluginArgs)
        except Exception as e:
            loge("Could not perform release! Rolling back ... (is SSH shell really open?!)", e)
            try:
                mvn("release:rollback")
            except Exception as e2:
                loge("Rolling back from failed release:perform failed! Ouch...", e2)
            finally:
                raise e


    def checkout(self, artifact, config, targetFolder):
        logd("Checking out artifact: %s" % artifact.artifactId)
        
        srcPath = artifact.svnPath + "/trunk"
        targetPath = os.path.join(targetFolder, artifact.artifactId)
        svn("checkout %s %s --username %s --password %s" % (srcPath, targetPath, config.username, config.password))

        return targetPath

# PYTHON CHEAT SHEET
#
# os.path.exists/isfile/isabs("bob.txt")
# os.getcwd()
# os.system("mvn clean verify")
# os.system("mvn -U -up -B ... clean release:prepare")
#from subprocess import call
    #call(["say", text])