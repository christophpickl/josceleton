from commons import webFileExists, formatArtifactRelativeUrlBy, removeSlashTrunk

class PostconditionChecker:
        
    def lookupErrorsForArtifact(self, artifact):
        tmpErrors = []
        tmpErrors.append(self.conditionArtifactDeployedToMavenReleaseRepo(artifact))
        tmpErrors.append(self.conditionSubversionTagWasCreated(artifact))
        
        # TODO print "please confirm you are seing XYZ"
        # hitEnter()
#        openOsx(some web url)
        
        return [e for e in tmpErrors if e != None]
    
    def conditionSubversionTagWasCreated(self, artifact):
        baseWebUrl = "josceleton.svn.sourceforge.net"
        baseSvnUrl = "/viewvc/josceleton"
        svnTagUrl = "%s/%s/tags/%s-%s/pom.xml" % (baseSvnUrl,
                                             removeSlashTrunk(artifact.svnRelativePath), # prototypes/release-playground
                                             artifact.artifactId, artifact.versionRelease)
        errorMessage = "No proper SVN tag was created: http://%s%s" % (baseWebUrl, svnTagUrl)
        return errorMessage if webFileExists(baseWebUrl, svnTagUrl) == False else None
    
    def conditionArtifactDeployedToMavenReleaseRepo(self, artifact):
        baseWebUrl = "josceleton.sourceforge.net"
        
        relativeToBase = formatArtifactRelativeUrlBy(artifact)
#        josceleton.sourceforge.net
        errorMessage = "Artifact was not deployed to maven release repository: http://%s%s" % (baseWebUrl, relativeToBase)
        return errorMessage if webFileExists(baseWebUrl, relativeToBase) == False else None 
    
        
