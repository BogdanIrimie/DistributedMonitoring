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

echo ------ Start building Converter component ------
mvn -Dmaven.test.skip=true package -f $projectPath/Converter;
echo ------ Finish Converter component build ------

echo ------ Start building Presenter component ------
mvn -Dmaven.test.skip=true package -f $projectPath/Presenter;
echo ------ Finish Presenter component build ------

cp $projectPath/FrontEnd/target/FrontEnd.jar $projectPath/deployment/specs_monitoring_nmap_frontend/lib

cp $projectPath/Scanner/target/Scanner.jar $projectPath/deployment/specs_monitoring_nmap_scanner/lib

cp $projectPath/Converter/target/Converter.jar $projectPath/deployment/specs_monitoring_nmap_converter/lib

cp $projectPath/Presenter/target/Presenter.jar $projectPath/deployment/specs_monitoring_nmap_presenter/lib
