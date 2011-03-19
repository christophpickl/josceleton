from commons import * #@UnusedWildImport
from sysexec import * #@UnusedWildImport

class PostconditionChecker:
        
    def lookupErrorsForArtifact(self, config, artifact):
        tmpErrors = []
        tmpErrors.append(self.conditionArtifactDeployedToMavenReleaseRepo(config, artifact))
        tmpErrors.append(self.conditionSubversionTagWasCreated(config, artifact))
        tmpErrors.append(self.conditionSiteDeployed(config, artifact))
        # TODO print "please confirm you are seing XYZ"
        # hitEnter()
#        openOsx(some web url)
        
        return [e for e in tmpErrors if e != None]
    
    def conditionSubversionTagWasCreated(self, config, artifact):
        # http://josceleton.svn.sourceforge.net/svnroot/josceleton/artifact/_README.txt
        svnTagUrl = "%s/%s/tags/%s-%s/pom.xml" % (
                            config.urlSvnWebRoot, # == http://josceleton.svn.sourceforge.net/svnroot/josceleton
                            artifact.svnRelativeToArtifactsBase, # == artifact/pom/corporate-pom
                            artifact.artifactId,
                            artifact.versionRelease
                            )
        if webFileExists(svnTagUrl) == False:
            errorMessage = "No proper SVN tag was created: %s" % svnTagUrl
            notifye("Post Condition Failed!", errorMessage)
            return errorMessage
        else:
            return None
    
    def conditionArtifactDeployedToMavenReleaseRepo(self, config, artifact):
        fileName = "%s-%s.%s" % (
                        artifact.artifactId, # corporate-pom
                        artifact.versionRelease, # 0.4
                        artifact.packaging # pom
                        )
        completeWebUrl = "%s/%s/%s/%s/%s" % (
                                 config.urlMavenReleaseRepo, # http://josceleton.sourceforge.net/maven/release
                                 artifact.groupId.replace(".", "/"), # net/sf/josceleton/pom
                                 artifact.artifactId, # corporate-pom
                                 artifact.versionRelease, # 0.4
                                 fileName # corporate-pom-0.4.pom
                                 )
        if webFileExists(completeWebUrl) == False:
            errorMessage = "Artifact was not deployed to Maven Repository: %s" % completeWebUrl
            notifye("Post Condition Failed!", errorMessage)
            return errorMessage
        else:
            return None
    
    def conditionSiteDeployed(self, config, artifact):
        completeWebUrl = "%s/%s/%s/index.html" % (
                                 config.urlDocWebRoot, # http://josceleton.sourceforge.net/documentation
                                 artifact.artifactId,
                                 artifact.versionRelease
                                 )
        if webFileExists(completeWebUrl) == False:
            errorMessage = "Site was not deployed to Webserver: %s" % completeWebUrl
            notifye("Post Condition Failed!", errorMessage)
            return errorMessage
        else:
            return None
        
        
        # /corporate-pom/0.3/index.html
    
        
