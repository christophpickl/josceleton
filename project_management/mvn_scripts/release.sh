#!/bin/bash

source includes/configuration.sh
source includes/common.sh


function main {
	for CURRENT_ARTIFACT_SINGLE_PATH in "${ARTIFACTS_SINGLE_PATH_ARRY[@]}"
	do :
		echo "--- processing artifact [${CURRENT_ARTIFACT_SINGLE_PATH}] ---"
		
		ARTIFACT_ABS_PATH=${BASE_PATH}/${CURRENT_ARTIFACT_SINGLE_PATH}
		log "  changing path to: ${ARTIFACT_ABS_PATH}"
		cd $ARTIFACT_ABS_PATH
		
		echo "mvn release:prepare"
		exec_cmd "mvn release:prepare"
		
		echo "mvn release:perform"
		exec_cmd "mvn release:perform"
		
		echo "  =======> project [${CURRENT_ARTIFACT_SINGLE_PATH}] DONE"
		echo
	done
	
	finalize_and_exit 0
}

function exec_cmd {
	P_CMD=$1
	
	log "  executing [${P_CMD}] for artifact [${CURRENT_ARTIFACT_SINGLE_PATH}]"
	${P_CMD}
	RET_CODE=$?
	
	if [ $RET_CODE -ne 0 ]; then
		echo # add additional empty line for readability
		finalize_and_exit $RET_CODE
	fi
}

function finalize_and_exit {
	P_RET_CODE=$1
	log "finalizing with return code ${P_RET_CODE}"
	
	if [ $P_RET_CODE -eq 0 ] ; then
		echo "Finished SUCCESSFULLY :)"
	else
		echo "FAIL!!! artifact path was [${ARTIFACT_ABS_PATH}]"
		echo
		echo "  ==> you should run: mvn release:rollback"
	fi
	
	cd $SCRIPT_DIR
	exit ${P_RET_CODE}
}

main
