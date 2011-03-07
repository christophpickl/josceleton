#!/bin/sh

cd guice_2.0/
echo deploying guice ...
./guice.deploy.sh

cd ../javaosc_1_0-20060402/
echo deploying javaosc ...
./javaoscfull.deploy.sh

cd ../..