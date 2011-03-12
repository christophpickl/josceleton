#!/bin/bash


#DEBUG=0
DEBUG=1

# assumes you have checked out the projects in the <BASE_PATH>.
BASE_PATH="/Users/phudy/_dev/pulse"

# each project is then specified with its folder name in <ARTIFACTS_PATH_ARRAY>.
ARTIFACTS_PATH_ARRAY=( "josceleton-corporate-pom" "josceleton-poms-reactor" "josceleton-reactor" )


ARTIFACTS_SINGLE_PATH_ARRY=( \
	\
	"josceleton-corporate-pom" \
	\
	"josceleton-poms-reactor/guice-dependencies" \
	"josceleton-poms-reactor/josceleton-checkstyle-config" \
	"josceleton-poms-reactor/java-abstract-pom" \
	"josceleton-poms-reactor/java-pom" \
	\
	"josceleton-reactor/commons" \
	"josceleton-reactor/core-api" \
	"josceleton-reactor/core-impl" \
	"josceleton-reactor/connection-api" \
	"josceleton-reactor/connection-impl" \
	"josceleton-reactor/josceleton" \
	)
