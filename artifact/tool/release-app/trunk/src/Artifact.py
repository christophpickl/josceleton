
class Artifact:
    def __init__(self, artifactId, groupId, svnRelativeToArtifactsBase, versionRelease, versionNext, packaging = "jar"):
        self.artifactId = artifactId
        self.groupId = groupId
        self.svnRelativeToArtifactsBase = svnRelativeToArtifactsBase
        self.versionRelease = versionRelease
        self.versionNext = versionNext
        self.packaging = packaging
        
    def __str__(self):
        return "'%s'" % self.artifactId

