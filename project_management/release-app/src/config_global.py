
SVN_BASE="https://josceleton.svn.sourceforge.net/svnroot/josceleton"

#SYSEXEC_ENABLED=True
SYSEXEC_ENABLED=False
PRECONDITIONS_ENABLED=True
#PRECONDITIONS_ENABLED=False
#INTERACTIVE=True
INTERACTIVE=False




class Project:
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


class PackageDescriptor:
    def __init__(self, ):
        self.artifactId = artifactId
        
    def __str__(self):
        return "'%s'" % self.artifactId