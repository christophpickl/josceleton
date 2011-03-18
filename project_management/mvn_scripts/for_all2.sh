#!/bin/bash


ARTIFACTS=( \
	"corporate-pom" \
	"java-abstract-pom" \
	"java-pom" \
	\
	"guice-dependencies" \
	"checkstyle-config" \
	\
	"josceleton-reactor" \
#	"console-prototype" \
#	"midi-prototype" \
)

for X in "${ARTIFACTS[@]}"
do :
	CMD="mvn clean install --file /Users/phudy/_dev/josc/${X}/pom.xml"
	echo $CMD
	$CMD
done