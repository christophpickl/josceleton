#!/bin/bash
source include_for_all_artifacts.sh

### C O N F I G U R A T I O N
################################################

DEBUG=0 # 0|1

# assumes you have checked out the projects in the <BASE_PATH>.
BASE_PATH="/Users/phudy/_dev/pulse"


# each project is then specified with its folder name in <ARTIFACTS_PATH_ARRAY>.
ARTIFACTS_PATH_ARRAY=( "josceleton-corporate-pom" "josceleton-poms-reactor" "josceleton-reactor" )
#ARTIFACTS_PATH_ARRAY=( "josceleton-poms-reactor" )

MVN_ARG="eclipse:eclipse install"
#MVN_ARG="install"
#MVN_ARG="clean eclipse:eclipse test site install"



PER_ARTIFACT_CMD="mvn ${MVN_ARG}"
################################################


#execute functionality
proccess_artifacts
