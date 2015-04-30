#!/bin/sh

env=printenv;

mkdir -p ../etc
exec > ../etc/conf.properties
echo '#RabbitMQ connection details'
$env | grep rabbitHost
$env | grep scRabbitSendQueue | sed 's/scRabbitSendQueue/rabbitSendQueue/g'
$env | grep scRabbitReceiveQueue | sed 's/scRabbitReceiveQueue/rabbitReceiveQueue/g'
$env | grep rabbitUser
$env | grep rabbitPassword
echo 
echo '#MongoDB connection details'
$env | grep mongoHost
$env | grep mongoPort

exec > ../var/specs_monitoring_nmap_scanner.pid
java -jar ../lib/Scanner.jar & echo $!
