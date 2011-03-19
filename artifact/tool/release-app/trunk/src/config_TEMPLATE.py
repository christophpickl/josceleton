from Artifact import Artifact
from Package import Package
from Reactor import Reactor

class Configuration:
    
    def __init_playground(self):
        print "INIT PLAYGROUND"
        
        # first release all production settings, just to get sure
        self.urlDocWebRoot = None
        self.artifacts = None
        self.reactor = None
        self.package = None

        self.urlDocWebRoot = "http://josceleton.sourceforge.net/documentation/delme"
        commonGroup = "net.sf.josceleton.playground.releaseapp.playground1"
        commonSvnBase = "playground/releaseapp-playground1"
        self.artifacts = [
            Artifact( "java-pom", commonGroup, commonSvnBase + "/java-pom", "0.4", "0.5-SNAPSHOT", "pom" ),
            Artifact( "model",    commonGroup, commonSvnBase + "/model",    "0.3", "0.4-SNAPSHOT", "jar" ),
            Artifact( "logic",    commonGroup, commonSvnBase + "/logic",    "0.4", "0.5-SNAPSHOT", "jar" ),
            Artifact( "app",      commonGroup, commonSvnBase + "/app",      "0.4", "0.5-SNAPSHOT", "jar" ),
        ]
        self.reactor = Reactor("reactor-pom", commonGroup, commonSvnBase + "/reactor-pom", "0.4")
        # remember when using Package(self.artifacts..), and processing artifacts failed, then outcommented and resumed, packager will only package half of it :-/
        self.package = Package(self.artifacts, "some_zip", "zip readme content." )
        
        
    def __init__(self):
        ## GLOBAL
        #################################################################
        self.urlMavenReleaseRepo = "http://josceleton.sourceforge.net/maven/release"
        self.urlSvnWebRoot = "http://josceleton.svn.sourceforge.net/svnroot/josceleton"
        self.urlDocWebRoot = "http://josceleton.sourceforge.net/documentation"
        self.urlRootRoot = "http://josceleton.sourceforge.net"
        
        ## PER USER
        #################################################################
        self.workspace = "/path/to/releaseapp/tmp_workspace"
        self.localSvnRoot = "/path/to/checkedout/svn/root/ARTIFACT"
        self.username = "user"
        self.password = "pass"
        
        ## MAIN CONFIG
        #################################################################
        guiceDepsGroupId = "net.sf.josceleton.thirdparty.com.google.code.guice"
        self.artifacts = [
            Artifact("corporate-pom",      "net.sf.josceleton", "pom/corporate-pom",          "0.4", "0.5-SNAPSHOT", "pom"),
            Artifact("guice-dependencies", guiceDepsGroupId,    "pom/guice-dependencies",     "2.3", "2.4-SNAPSHOT", "pom"),
            Artifact("checkstyle-config",  "net.sf.josceleton", "pom/checkstyle-config",      "0.4", "0.5-SNAPSHOT", "jar"),
            Artifact("java-abstract-pom",  "net.sf.josceleton", "pom/java-abstract-pom",      "0.4", "0.5-SNAPSHOT", "pom"),
            Artifact("java-pom",           "net.sf.josceleton", "pom/java-pom",               "0.4", "0.5-SNAPSHOT", "pom"),
            
            Artifact("commons",            "net.sf.josceleton", "josceleton/commons",         "0.4", "0.5-SNAPSHOT", "jar"),
            Artifact("core-api",           "net.sf.josceleton", "josceleton/core-api",        "0.4", "0.5-SNAPSHOT", "jar"),
            Artifact("core-impl",          "net.sf.josceleton", "josceleton/core-impl",       "0.4", "0.5-SNAPSHOT", "jar"),
            Artifact("connection-api",     "net.sf.josceleton", "josceleton/connection-api",  "0.4", "0.5-SNAPSHOT", "jar"),
            Artifact("connection-impl",    "net.sf.josceleton", "josceleton/connection-impl", "0.4", "0.5-SNAPSHOT", "jar"),
            Artifact("motion-api",         "net.sf.josceleton", "josceleton/motion-api",      "0.1", "0.2-SNAPSHOT", "jar"),
            Artifact("motion-impl",        "net.sf.josceleton", "josceleton/motion-impl",     "0.1", "0.2-SNAPSHOT", "jar"),
            Artifact("josceleton",         "net.sf.josceleton", "josceleton/josceleton",      "0.4", "0.5-SNAPSHOT", "jar")
        ]
        self.reactor = Reactor("josceleton-reactor", "net.sf.josceleton", "josceleton/josceleton-reactor", "0.4" )
        self.package = Package(self.artifacts, "josceleton-0.4", "This is the content of my readme file." )

        ## PLAYGROUND OVERRIDE
        #################################################################
#        self.__init_playground()

