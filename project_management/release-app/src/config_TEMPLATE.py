from config_global import SVN_BASE

class Configuration:

    def __init__(self):
        
        self.workspace = "/path/to/temporary/workspace"
        self.username = "john_smith"
        self.password = "tops3cr3t"
        self.projects = [
                Project(
                    "release-playground",
                    "net.sf.josceleton.delme.playground.release",
                    "prototypes/release-playground", 
                    "0.3", "0.4-SNAPSHOT",
                    "pom"
                )
            ]
        self.sayEnabled = False # execute fancy osx say command ;)

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