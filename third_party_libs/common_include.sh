#!/bin/sh

#DEPS_IDS=( "GUICE_GUICE" "GUICE_AOP" "GUICE_AINJECT" "GUICE_GRAPHER" "JAVAOSC" )
DEPS_IDS=( "GUICE_AOP" "GUICE_AINJECT" "GUICE_GRAPHER" "JAVAOSC" )

GUICE_PATH="guice_2.0/"
DEP_GUICE_GUICE_JAR="${GUICE_PATH}guice-2.0.jar"
DEP_GUICE_GUICE_ARTIFACTID="guice"
DEP_GUICE_GUICE_GROUPID="com.google.code.guice"
DEP_GUICE_GUICE_VERSION="2.0-josceleton"

DEP_GUICE_AOP_JAR="${GUICE_PATH}aopalliance.jar"
DEP_GUICE_AOP_ARTIFACTID="aopalliance"
DEP_GUICE_AOP_GROUPID="com.google.code.guice"
DEP_GUICE_AOP_VERSION="2.0-josceleton"

DEP_GUICE_AINJECT_JAR="${GUICE_PATH}guice-assistedinject-2.0.jar"
DEP_GUICE_AINJECT_ARTIFACTID="guice-assistedinject"
DEP_GUICE_AINJECT_GROUPID="com.google.code.guice"
DEP_GUICE_AINJECT_VERSION="2.0-josceleton"
                        
DEP_GUICE_GRAPHER_JAR="${GUICE_PATH}guice-grapher-2.0.jar"
DEP_GUICE_GRAPHER_ARTIFACTID="guice-grapher"
DEP_GUICE_GRAPHER_GROUPID="com.google.code.guice"
DEP_GUICE_GRAPHER_VERSION="2.0-josceleton"
                         
DEP_JAVAOSC_JAR="javaosc_1_0-20060402/javaoscfull-20060402.jar"
DEP_JAVAOSC_ARTIFACTID="josceleton"
DEP_JAVAOSC_GROUPID="com.illposed"
DEP_JAVAOSC_VERSION="1.0-20060402-josceleton"

function mvn_internal {
	PARAM_MVN_CMD=${1}
	
	for DEPS_ID in "${DEPS_IDS[@]}"
	do :
		DEP_JAR="DEP_${DEPS_ID}_JAR"; eval DEP_JAR=\$$DEP_JAR
		DEP_ARTIFACTID="DEP_${DEPS_ID}_ARTIFACTID"; eval DEP_ARTIFACTID=\$$DEP_ARTIFACTID
		DEP_GROUPID="DEP_${DEPS_ID}_GROUPID"; eval DEP_GROUPID=\$$DEP_GROUPID
		DEP_VERSION="DEP_${DEPS_ID}_VERSION"; eval DEP_VERSION=\$$DEP_VERSION
		
		MVN_ARGS=""
		MVN_ARGS="${MVN_ARGS} -Dpackaging=jar"
		MVN_ARGS="${MVN_ARGS} -DgeneratePom=true"
		MVN_ARGS="${MVN_ARGS} -Dversion=${DEP_VERSION}"
		MVN_ARGS="${MVN_ARGS} -DartifactId=${DEP_ARTIFACTID}"
		MVN_ARGS="${MVN_ARGS} -Dfile=${DEP_JAR}"
		MVN_ARGS="${MVN_ARGS} -DgroupId=${DEP_GROUPID}"
		
		if [ "$PARAM_MVN_CMD" == "mvn deploy:deploy-file" ]; then
			MVN_ARGS="${MVN_ARGS} -DrepositoryId=josceleton-sourceforge-server"
			MVN_ARGS="${MVN_ARGS} -Durl=scp://shell.sourceforge.net/home/project-web/josceleton/htdocs/maven/release"
		fi
		
		FULL_CMD="${PARAM_MVN_CMD} ${MVN_ARGS}"
		echo $FULL_CMD
		$FULL_CMD
	done
}

function mvn_install {
	mvn_internal "mvn install:install-file"
}

function mvn_deploy {
	mvn_internal "mvn deploy:deploy-file"
}
