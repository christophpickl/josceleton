
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
        
        buildFolderName = package.zipName
        buildFolder = os.path.join(targetFolder, buildFolderName)
        mkdir(buildFolder)
        
        libsFolderName = "libs"
        libsFolder = os.path.join(buildFolder, libsFolderName)
        mkdir(libsFolder)
        libsFolderRelativePath = os.path.join(buildFolder, libsFolderName)
        logd("Downloading %i artifacts" % len(package.artifacts))
        self.downloadArtifacts(package.artifacts, libsFolderRelativePath)
        
        readmeFile = "%s/README.txt" % buildFolder
        writeFile(readmeFile, package.readmeContent)
        sysexec("zip -r %s.zip %s/ " % (package.zipName, package.zipName))

    def downloadArtifacts(self, artifacts, libsFolder):
        for artifact in artifacts:
            downloadedFileName = formatArtifactNameBy(artifact)
            artifactUrl = "%s%s" % (URL_ROOT, formatArtifactRelativeUrlBy(artifact))
            wgetTarget = "%s/%s" % (libsFolder, downloadedFileName)
            wget("--output-document=%s %s" % (wgetTarget, artifactUrl))
            logd("Downloaded artifact: %s" % downloadedFileName)
