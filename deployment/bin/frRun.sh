#!/bin/sh

env=printenv;

mkdir -p ../etc
exec > ../etc/frConf.properties
echo '#RabbitMQ connection details'
$env | grep rabbitHost
$env | grep frRabbitSendQueue | sed 's/frRabbitSendQueue/rabbitSendQueue/g'
$env | grep rabbitUser
$env | grep rabbitPassword
echo 
echo '#MongoDB connection details'
$env | grep mongoHost
$env | grep mongoPort

exec > ../lib/frPid
java -jar ../lib/FrontEnd.jar & echo $!
