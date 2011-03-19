
class Artifact:
    def __init__(self, artifactId, groupId, svnRelativeToRoot, versionRelease, versionNext, packaging = "jar"):
        self.artifactId = artifactId
        self.groupId = groupId
        self.svnRelativeToRoot = "artifact/" + svnRelativeToRoot
        self.svnRelativeToLocalArtifact = svnRelativeToRoot
        self.versionRelease = versionRelease
        self.versionNext = versionNext
        self.packaging = packaging
        
    def __str__(self):
        return "'%s'" % self.artifactId

