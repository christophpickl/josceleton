import datetime
import time

from config_global import PRECONDITIONS_ENABLED
from commons import * #@UnusedWildImport
from PreconditionChecker import PreconditionChecker
from PostconditionChecker import PostconditionChecker

        #* redirect output from process

        # post tasks
        # =================
        # check existence of artifacts via web (documentation/)
        # sourceforge deploy File
        # sourceforge README (optional nochmal als grosses ding dazu (nicht per projekt)
        
class Release:
    
    def createNewWith(self, config):
        timeStart = datetime.datetime.now()
        # LUXURY pause timer when waiting for user interaction
        
        # TODO validate if mvn2 is active (not mvn3, as wagon SCP provider does not work)
        # TODO confirm projects configuration by user!!!
        
        preConditions = PreconditionChecker()
        
        
        if PRECONDITIONS_ENABLED == True:
            if preConditions.areSatisfied(config) == False:
                loge("There were unsatisfied preconditions!")
                return
        
        targetFolder = self.prepareTargetFolder(config.workspace)
        for i, project in enumerate(config.projects):
            try:
                logi("---------------------- Releasing project '%s' verson %s (%i of %i)" %
                     (project.artifactId, project.versionRelease, i+1, len(config.projects)))
                if config.sayEnabled: say("releasing project %s" % project.artifactId)
                
                self.processProject(project, config, targetFolder)
                self.validatePostconditions(project)
                
                logi("---------------------- Finished releasing '%s'" % project.artifactId)
                
            except Exception as e:
                loge("Error while releasing project %s: %s" % (project, e.message), e)
                break
        
        timeEnd = datetime.datetime.now()
        duration = timeEnd - timeStart
        logi()
        logi("SUCCESS! (needed %s)" % duration)
    
    def validatePostconditions(self, project):
        logd("Checking post-conditions ...")
        failedPostCond = PostconditionChecker().lookupErrorsForProject(project)
        if len(failedPostCond) == 0:
            return
        raise Exception("Failed postconditions for project %s!\n%s" % (project, "\n".join(failedPostCond)))
    
    def prepareTargetFolder(self, workspacePath):
        if os.path.isdir(workspacePath) == 0 and os.path.isabs(workspacePath):
            raise Exception("Invalid workspace directory: %s" % workspacePath) 
        
        targetFolderName = time.strftime("%Y-%m-%d_%H-%M-%S") #http://docs.python.org/library/time.html#time.strftime 
        targetFolder = os.path.join(workspacePath, targetFolderName)
        mkdir(targetFolder)
        return targetFolder
    
    def processProject(self, project, config, targetFolder):
        checkoutFolder = self.checkout(project, config, targetFolder)
        
        chdir(checkoutFolder)
        releasePluginArgs = " ".join([
             # common release plugin options (http://maven.apache.org/plugins/maven-release-plugin/prepare-mojo.html)
             "-Dusername=%s" % config.username,
             "-Dpassword=%s" % config.password,
             ("-DscmCommentPrefix=\"[release-app] releasing verison '%s' of artifact '%s'\"" %
              (project.versionRelease, project.artifactId)),
             
             # release:prepare specific options
             "-DallowTimestampedSnapshots=false", # default=false
             "-DreleaseVersion=%s" % project.versionRelease,
             "-DdevelopmentVersion=%s" % project.versionNext,
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
                # --resume-from <arg> # Resume reactor from specified project
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


    def checkout(self, project, config, targetFolder):
        logd("Checking out project: %s" % project.artifactId)
        
        srcPath = project.svnPath + "/trunk"
        targetPath = os.path.join(targetFolder, project.artifactId)
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