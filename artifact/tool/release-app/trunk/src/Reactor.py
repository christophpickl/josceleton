
class Reactor(object):
    
    def __init__(self, artifactId, groupId, svnRelativeToArtifactsBase, versionRelease ):
        self.artifactId = artifactId
        self.groupId = groupId
        self.svnRelativeToArtifactsBase = svnRelativeToArtifactsBase
        self.versionRelease = versionRelease
    
    def __str__(self):
        return "'%s'" % self.artifactId
    