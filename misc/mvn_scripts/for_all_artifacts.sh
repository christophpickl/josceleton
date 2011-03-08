#!/bin/bash
source include_for_all_artifacts.sh

### C O N F I G U R A T I O N
################################################
DEBUG=0 # ... 0|1
BASE_PATH="/Users/phudy/_dev/pulse"


ARTIFACTS_PATH_ARRAY=( "josceleton-corporate-pom" "josceleton-poms-reactor" "josceleton-reactor" )
#ARTIFACTS_PATH_ARRAY=( "josceleton-poms-reactor" )
#MVN_ARG="install"
#MVN_ARG="clean eclipse:eclipse test site install"
MVN_ARG="eclipse:eclipse install"



PER_ARTIFACT_CMD="mvn ${MVN_ARG}"

################################################
proccess_artifacts
