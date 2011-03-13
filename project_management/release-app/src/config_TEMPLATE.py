from config_global import Project

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

    def getPackageDescriptor(self):
        return None
