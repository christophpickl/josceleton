
class Package:
    
    def __init__(self, artifacts, artifact3rds, zipName, readmeContent):
        self.artifacts = artifacts
        self.artifact3rds = artifact3rds
        self.zipName = zipName
        self.readmeContent = readmeContent
        
    def __str__(self):
        return "Package '%s'" % self.zipName
