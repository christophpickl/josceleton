
class Package:
    
    def __init__(self, artifacts, zipName, readmeContent):
        self.artifacts = artifacts
        self.zipName = zipName
        self.readmeContent = readmeContent
        
    def __str__(self):
        return "Package '%s'" % self.zipName
