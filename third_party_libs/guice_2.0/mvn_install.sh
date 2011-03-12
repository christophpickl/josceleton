#!/bin/sh
#
# standalone scripts to install 3rd party dependencies to local repository

mvn install:install-file -Dfile=aopalliance.jar \
                         -DgroupId=com.google.code.guice \
                         -DartifactId=aopalliance \
                         -Dversion=2.0-josceleton \
                         -Dpackaging=jar \
                         -DgeneratePom=true
mvn install:install-file -Dfile=guice-2.0.jar \
                         -DgroupId=com.google.code.guice \
                         -DartifactId=guice \
                         -Dversion=2.0-josceleton \
                         -Dpackaging=jar \
                         -DgeneratePom=true
mvn install:install-file -Dfile=guice-assistedinject-2.0.jar \
                         -DgroupId=com.google.code.guice \
                         -DartifactId=guice-assistedinject \
                         -Dversion=2.0-josceleton \
                         -Dpackaging=jar \
                         -DgeneratePom=true
mvn install:install-file -Dfile=guice-grapher-2.0.jar \
                         -DgroupId=com.google.code.guice \
                         -DartifactId=guice-grapher \
                         -Dversion=2.0-josceleton \
                         -Dpackaging=jar \
                         -DgeneratePom=true