#!/bin/bash


export MAVEN_OPTS="-Xms256m -Xmx700m"
mvn clean
mvn site
mvn dashboard:dashboard