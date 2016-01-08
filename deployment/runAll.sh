#!/bin/bash

deploymentPath=$(pwd);
echo $deploymentPath

java -jar $deploymentPath/specs_monitoring_nmap_frontend/lib/FrontEnd.jar &
disown

java -jar $deploymentPath/specs_monitoring_nmap_scanner/lib/Scanner.jar &
disown

java -jar $deploymentPath/specs_monitoring_nmap_converter/lib/Converter.jar &
disown

java -jar $deploymentPath/specs_monitoring_nmap_presenter/lib/Presenter.jar &
disown
