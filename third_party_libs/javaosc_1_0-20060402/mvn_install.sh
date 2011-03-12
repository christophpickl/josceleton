#!/bin/sh
#
# standalone scripts to install 3rd party dependency to local repository

mvn install:install-file -Dfile=javaoscfull-20060402.jar \
                         -DgroupId=com.illposed \
                         -DartifactId=javaosc \
                         -Dversion=1.0-20060402-josceleton \
                         -Dpackaging=jar \
                         -DgeneratePom=true