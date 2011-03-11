#!/bin/bash

source includes/configuration.sh
source includes/common.sh
source includes/for_all_artifacts.sh

### C O N F I G U R A T I O N
################################################

MVN_ARG="clean"
#MVN_ARG="eclipse:eclipse install"
#MVN_ARG="clean eclipse:eclipse test site install"


PER_ARTIFACT_CMD="mvn ${MVN_ARG}"
################################################

#execute functionality
proccess_artifacts
