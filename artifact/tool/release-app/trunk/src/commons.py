import httplib
import os
import time
from logging import * #@UnusedWildImport
from config_global import SYSEXEC_ENABLED
from sysexec import mkdir

# returns: "/maven/release/net/sf/josceleton/delme/playground/release/release-playground/0.2/release-playground-0.2.pom"
def formatArtifactRelativeUrlBy(artifact):
    baseWebMavenReleaseUrl = "/maven/release"
    groupIdToPath = artifact.groupId.replace(".", "/") # net/sf/josceleton/delme/playground/release
    artifactFileName = "%s-%s.%s" % (artifact.artifactId, artifact.versionRelease, artifact.packaging) # release-playground-0.2.pom
    
    relativeToBase = "%s/%s/%s/%s/%s" % (baseWebMavenReleaseUrl, groupIdToPath, artifact.artifactId, artifact.versionRelease, artifactFileName)
    return relativeToBase

def formatArtifactNameBy(artifact):
    return "%s-%s.%s" % (artifact.artifactId, artifact.versionRelease, artifact.packaging) # release-playground-0.2.pom
    

def prepareTargetFolder(workspacePath, suffix):
    targetFolderNameTimePart = time.strftime("%d-%H_%M_%S") #http://docs.python.org/library/time.html#time.strftime
    targetFolderName = "%s-%s" % (targetFolderNameTimePart, suffix) 
    targetFolder = os.path.join(workspacePath, targetFolderName)
    mkdir(targetFolder)
    return targetFolder

def webFileExists(fullUrl):
    logd("Checking if web file exists: %s" % fullUrl)
    if SYSEXEC_ENABLED == False: return True

    baseWithoutProtocol = fullUrl[len("http://"):]
    idxBaseSlash = baseWithoutProtocol.index("/")
    baseWebUrl = fullUrl[len("http://"):idxBaseSlash + len("http://")]
    fileToCheck = fullUrl[len("http://") + idxBaseSlash:]
    logt("Base URL [%s]; File URL [%s]" % (baseWebUrl, fileToCheck))
    connection = httplib.HTTPConnection(baseWebUrl)
    connection.request("HEAD", fileToCheck)
    response = connection.getresponse()
    connection.close()
    return response.status == 200

    
def removeSlashTrunk(fromSvnUrl):
    token = "/trunk"
    idxToken = fromSvnUrl.find(token)
    if idxToken == -1:
        return fromSvnUrl
    
    idxAfterToken = idxToken + len(token)
    return fromSvnUrl[0:idxToken] + fromSvnUrl[idxAfterToken:]
