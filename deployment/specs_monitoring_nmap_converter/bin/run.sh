#!/bin/sh

env=printenv;

mkdir -p ../etc
exec > ../etc/conf.properties
echo '#RabbitMQ connection details'
$env | grep rabbitHost
$env | grep coRabbitSendQueue | sed 's/coRabbitSendQueue/rabbitSendQueue/g'
$env | grep coRabbitReceiveQueue | sed 's/coRabbitReceiveQueue/rabbitReceiveQueue/g'
$env | grep rabbitUser
$env | grep rabbitPassword
echo 
echo '#MongoDB connection details'
$env | grep mongoHost
$env | grep mongoPort

exec > ../var/specs_monitoring_nmap_converter.pid
java -jar ../lib/Converter.jar & echo $!
