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
    

def prepareTargetFolder(workspacePath):
    if os.path.isdir(workspacePath) == 0 and os.path.isabs(workspacePath):
        raise Exception("Invalid workspace directory: %s" % workspacePath)
    
    targetFolderName = time.strftime("%Y-%m-%d_%H-%M-%S") #http://docs.python.org/library/time.html#time.strftime 
    targetFolder = os.path.join(workspacePath, targetFolderName)
    mkdir(targetFolder)
    return targetFolder

def webFileExists(baseWebUrl, fileToCheck):
    logd("Checking if web file exists: http://%s%s" % (baseWebUrl, fileToCheck))
    if SYSEXEC_ENABLED == False: return True
    
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
