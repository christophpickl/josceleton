#!/bin/bash

DEBUG=0 # ... 0|1

function log {
	if [ $DEBUG -eq 1 ] ; then
		echo "[LOG] ${1}"
	fi
}

function print_duration {
	TIME_END=`date +"%s"`
	DURATION=`expr $TIME_END - $TIME_START`
	SECONDS_LEFT=`expr $DURATION % 60`
	MINUTES=`expr $DURATION / 60`
	FORMATTED_DATE="${MINUTES} min${OPTIONAL_MIN_S} ${SECONDS_LEFT}secs"
	
	echo "  // script execution took: ${FORMATTED_DATE}"
}

function finalize_and_exit {
	P_RET_CODE=$1
	log "finalizing with return code ${P_RET_CODE}"
	
	print_duration
	
	if [ $P_RET_CODE -eq 0 ] ; then
		echo "Finished SUCCESSFULLY :)"
	else
		echo "FAIL!!! artifact path was [${ARTIFACT_ABS_PATH}]"
	fi
	
	cd $SCRIPT_DIR
	exit ${P_RET_CODE}
}

### C O N F I G U R A T I O N
################################################


#MVN_ARG="install"
#ARTIFACTS_PATH_ARRAY=( "josceleton-poms-reactor" )

ARTIFACTS_PATH_ARRAY=( "josceleton-poms-reactor" "josceleton-corporate-pom" "josceleton-reactor" )
#MVN_ARG="clean eclipse:eclipse test site install"
MVN_ARG="eclipse:clean eclipse:eclipse"




PER_ARTIFACT_CMD="mvn ${MVN_ARG}"
BASE_PATH="/Users/phudy/_dev/pulse"

### I N T E R N A L
################################################
TIME_START=`date +"%s"`
SCRIPT_DIR=`pwd`

for CURRENT_ARTIFACT_PATH in "${ARTIFACTS_PATH_ARRAY[@]}"
do :
	echo "--- processing artifact [${CURRENT_ARTIFACT_PATH}] ---"
	
	ARTIFACT_ABS_PATH=${BASE_PATH}/${CURRENT_ARTIFACT_PATH}
	log "  changing path to: ${ARTIFACT_ABS_PATH}"
	cd $ARTIFACT_ABS_PATH
	
	log "  executing [${PER_ARTIFACT_CMD}] for artifact [${CURRENT_ARTIFACT_PATH}]"
	${PER_ARTIFACT_CMD}
	RET_CODE=$?
	
	if [ $RET_CODE -ne 0 ]; then
		echo # add additional empty line for readability
		finalize_and_exit $RET_CODE
	fi
	echo "  =======> project [${CURRENT_ARTIFACT_PATH}] DONE"
	echo
done

finalize_and_exit 0
