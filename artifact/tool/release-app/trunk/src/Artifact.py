
class Artifact:
    def __init__(self, artifactId, groupId, svnRelativeToBase, versionRelease, versionNext, packaging = "jar"):
        self.artifactId = artifactId
        self.groupId = groupId
        self.svnRelativeToBase = svnRelativeToBase
        self.versionRelease = versionRelease
        self.versionNext = versionNext
        self.packaging = packaging
        
    def __str__(self):
        return "'%s'" % self.artifactId
