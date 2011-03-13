from commons import webFileExists

class PostconditionChecker:
        
    def lookupErrorsForProject(self, project):
        tmpErrors = []
        tmpErrors.append(self.conditionArtifactDeployedToMavenReleaseRepo(project))
        tmpErrors.append(self.conditionSubversionTagWasCreated(project))
        return [e for e in tmpErrors if e != None]
    
    def conditionSubversionTagWasCreated(self, project):
        baseWebUrl = "josceleton.svn.sourceforge.net"
        baseSvnUrl = "/viewvc/josceleton"
        svnTagUrl = "%s/%s/tags/%s-%s/pom.xml" % (baseSvnUrl,
                                             project.svnRelativePath, # prototypes/release-playground
                                             project.artifactId, project.versionRelease)
        errorMessage = "No proper SVN tag was created: http://%s%s" % (baseWebUrl, svnTagUrl)
        return errorMessage if webFileExists(baseWebUrl, svnTagUrl) == False else None
    
    def conditionArtifactDeployedToMavenReleaseRepo(self, project):
        baseWebUrl = "josceleton.sourceforge.net"
        baseWebMavenReleaseUrl = "/maven/release" # http://josceleton.sourceforge.net/maven/release
        groupIdToPath = project.groupId.replace(".", "/") # net/sf/josceleton/delme/playground/release
        artifactFileName = "%s-%s.%s" % (project.artifactId, project.versionRelease, project.packaging) # release-playground-0.2.pom
#        josceleton.sourceforge.net/maven/release/net/sf/josceleton/delme/playground/release/release-playground/0.2/release-playground-0.2.pom
        relativeToBase = "%s/%s/%s/%s/%s" % (baseWebMavenReleaseUrl, groupIdToPath,
                                             project.artifactId, project.versionRelease, artifactFileName)
        errorMessage = "Artifact was not deployed to maven release repository: http://%s%s" % (baseWebUrl, relativeToBase)
        return errorMessage if webFileExists(baseWebUrl, relativeToBase) == False else None 
            
        
