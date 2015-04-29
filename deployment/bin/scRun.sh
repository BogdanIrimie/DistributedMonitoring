#!/bin/sh

env=printenv;

mkdir -p ../etc
exec > ../etc/scConf.properties
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

exec > ../lib/scPid
java -jar ../lib/Scanner.jar & echo $!
