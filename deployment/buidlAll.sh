#!/bin/bash

cd ..
projectPath=$(pwd);
echo $projectPath;

# Maven build.
echo ------ Start building FrontEnd component ------
mvn -Dmaven.test.skip=true package -f $projectPath/FrontEnd;
echo ------ Finish FrontEnd component build ------

echo ------ Start building Scheduler component ------
mvn -Dmaven.test.skip=true package -f $projectPath/Scheduler;
echo ------ Finish Scheduler component build ------

echo ------ Start building Scanner component ------
mvn -Dmaven.test.skip=true package -f $projectPath/Scanner;
echo ------ Finish Scanner component build ------

echo ------ Start building Converter component ------
mvn -Dmaven.test.skip=true package -f $projectPath/Converter;
echo ------ Finish Converter component build ------

echo ------ Start building Presenter component ------
mvn -Dmaven.test.skip=true package -f $projectPath/Presenter;
echo ------ Finish Presenter component build ------

# Copy .jar files to deployment folder.
cp $projectPath/FrontEnd/target/FrontEnd.jar $projectPath/deployment/specs_monitoring_nmap_frontend/lib
cp $projectPath/Scheduler/target/Scheduler.jar $projectPath/deployment/specs_monitoring_nmap_scheduler/lib
cp $projectPath/Scanner/target/Scanner.jar $projectPath/deployment/specs_monitoring_nmap_scanner/lib
cp $projectPath/Converter/target/Converter.jar $projectPath/deployment/specs_monitoring_nmap_converter/lib
cp $projectPath/Presenter/target/Presenter.jar $projectPath/deployment/specs_monitoring_nmap_presenter/lib

# Remove all logs from the deployment archives.
rm -rf $projectPath/deployment/specs_monitoring_nmap_frontend/var/*
rm -rf $projectPath/deployment/specs_monitoring_nmap_scheduler/var/*
rm -rf $projectPath/deployment/specs_monitoring_nmap_scanner/var/*
rm -rf $projectPath/deployment/specs_monitoring_nmap_converter/var/*
rm -rf $projectPath/deployment/specs_monitoring_nmap_presenter/var/*

# Create deployment archives for all components.
tar -czf deployment/specs_monitoring_nmap_frontend.tar.gz -C $projectPath/deployment ./specs_monitoring_nmap_frontend
tar -czf deployment/specs_monitoring_nmap_scheduler.tar.gz -C $projectPath/deployment ./specs_monitoring_nmap_scheduler
tar -czf deployment/specs_monitoring_nmap_scanner.tar.gz -C $projectPath/deployment ./specs_monitoring_nmap_scanner
tar -czf deployment/specs_monitoring_nmap_converter.tar.gz -C $projectPath/deployment ./specs_monitoring_nmap_converter
tar -czf deployment/specs_monitoring_nmap_presenter.tar.gz -C $projectPath/deployment ./specs_monitoring_nmap_presenter

echo ------ Build finished ------
