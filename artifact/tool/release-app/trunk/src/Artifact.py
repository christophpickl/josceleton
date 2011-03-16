from config_global import SVN_BASE

class Artifact:
    def __init__(self, artifactId, groupId, svnRelativePath, versionRelease, versionNext, packaging = "jar"):
        self.artifactId = artifactId
        self.groupId = groupId
        self.svnPath = SVN_BASE + "/" + svnRelativePath
        self.svnRelativePath = svnRelativePath
        self.versionRelease = versionRelease
        self.versionNext = versionNext
        self.packaging = packaging
        
    def __str__(self):
        return "'%s'" % self.artifactId

class Artifact3rd:
    def __init__(self, artifactId, groupId, version):
        self.artifactId = artifactId
        self.groupId = groupId
        self.version = version
        self.versionRelease = version
        self.packaging = "jar"
