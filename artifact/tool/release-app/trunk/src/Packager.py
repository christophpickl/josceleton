
from commons import * #@UnusedWildImport
from config_global import * #@UnusedWildImport
from sysexec import * #@UnusedWildImport

#  TODO
#  [ ]   source.zip => eg: core-api/src/main/java/...
#  [ ]   upload to sourceforge!

class Packager():
    
    def wrapUp(self, config):
        logi("Packaging artifacts.")
        
        targetFolder = prepareTargetFolder(config.workspace, "packager")
        chdir(targetFolder)
        
        package = config.package
        
        buildFolder = os.path.join(targetFolder, package.zipName)
        mkdir(buildFolder)
        
        libsFolderName = "libs"
        libsFolder = os.path.join(buildFolder, libsFolderName)
        mkdir(libsFolder)
        libsFolderRelativePath = os.path.join(buildFolder, libsFolderName)
        logd("Downloading %i artifacts" % len(package.artifacts))
        self.downloadArtifacts(config.urlRootRoot, package.artifacts, libsFolderRelativePath)
        
        readmeFile = "%s/README.txt" % buildFolder
        writeFile(readmeFile, package.readmeContent)
        sysexec("zip -r %s.zip %s/ " % (package.zipName, package.zipName))
        
        finishedZipPath = "%s/%s.zip" % (targetFolder, package.zipName)
        notifyi("Package Done", "See: %s" % finishedZipPath)

    def downloadArtifacts(self, urlRootRoot, artifacts, libsFolder):
        for artifact in artifacts:
            downloadedFileName = formatArtifactNameBy(artifact)
            artifactUrl = "%s%s" % (urlRootRoot, formatArtifactRelativeUrlBy(artifact))
            wgetTarget = "%s/%s" % (libsFolder, downloadedFileName)
            wget("--output-document=%s %s" % (wgetTarget, artifactUrl))
            logd("Downloaded artifact: %s" % downloadedFileName)
