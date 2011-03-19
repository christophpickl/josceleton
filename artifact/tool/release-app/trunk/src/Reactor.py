
class Reactor(object):
    
    def __init__(self, artifactId, groupId, svnRelativeToRoot, versionRelease ):
        self.artifactId = artifactId
        self.groupId = groupId
        self.svnRelativeToRoot = "artifact/" + svnRelativeToRoot
        self.svnRelativeToLocalArtifact = svnRelativeToRoot
        self.versionRelease = versionRelease
    
    def __str__(self):
        return "'%s'" % self.artifactId
    