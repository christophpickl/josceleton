#!/bin/bash


# about SF file release: https://sourceforge.net/apps/trac/sourceforge/wiki/Release%20files%20for%20download#SCP
# about markdown format: http://daringfireball.net/projects/markdown/syntax#overview


############ CONFIG start

#README_FILE="josceleton_0.2.md"
#TARGET_MIDDLE_PATH="/Josceleton/0.2"

README_FILE="Internal--Prototype--MidiRoute.md"
TARGET_MIDDLE_PATH="/Internal/Prototype/MidiRoute"



USER="christoph_pickl"
############ CONFIG end


REMOTE_BASE_PATH="/home/frs/project/j/jo/josceleton"
REMOTE_VISIBLE_PATH="${TARGET_MIDDLE_PATH}/README.md"
REMOTE_FULL_PATH="${REMOTE_BASE_PATH}${REMOTE_VISIBLE_PATH}"

CMD="scp ${README_FILE} ${USER},josceleton@frs.sourceforge.net:${REMOTE_FULL_PATH}"

echo "Deploying File: ${README_FILE}"
echo "To Remote Path: ${REMOTE_VISIBLE_PATH}"
echo "As User: ${USER}"
echo
echo ">> " $CMD
$CMD

echo
echo "DONE"
echo