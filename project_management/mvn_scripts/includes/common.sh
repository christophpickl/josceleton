#!/bin/bash

function log {
	if [ $DEBUG -eq 1 ] ; then
		echo "[LOG] ${1}"
	fi
}
