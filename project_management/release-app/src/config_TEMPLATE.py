from Artifact import Artifact

class Configuration:

    def __init__(self):
        
        self.workspace = "/path/to/temporary/workspace"
        self.username = "john_smith"
        self.password = "tops3cr3t"
        self.sayEnabled = False # execute fancy osx say command ;)
        self.artifacts = [
                Artifact(
                    "release-playground",
                    "net.sf.josceleton.delme.playground.release",
                    "prototypes/release-playground", 
                    "0.3", "0.4-SNAPSHOT",
                    "pom"
                )
            ]
        
        self.package = None
        
