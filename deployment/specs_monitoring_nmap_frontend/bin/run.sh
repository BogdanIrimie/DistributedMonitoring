#!/bin/sh

env=printenv;

mkdir -p ../etc
exec > ../etc/conf.properties
echo '#RabbitMQ connection details'
$env | grep rabbitHost
$env | grep frRabbitSendQueue | sed 's/frRabbitSendQueue/rabbitSendQueue/g'
$env | grep rabbitUser
$env | grep rabbitPassword
echo 
echo '#MongoDB connection details'
$env | grep mongoHost
$env | grep mongoPort

exec > ../var/specs_monitoring_nmap_frontend.pid
java -jar ../lib/FrontEnd.jar & echo $!
