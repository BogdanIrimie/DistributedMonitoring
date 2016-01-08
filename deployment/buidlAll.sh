#!/bin/bash

cd ..
projectPath=$(pwd);
echo $projectPath;

echo ------ Start building FrontEnd component ------
mvn -Dmaven.test.skip=true package -f $projectPath/FrontEnd;
echo ------ Finish FrontEnd component build ------

echo ------ Start building Scanner component ------
mvn -Dmaven.test.skip=true package -f $projectPath/Scanner;
echo ------ Finish Scanner component build ------
