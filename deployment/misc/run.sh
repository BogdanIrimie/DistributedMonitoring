#!/bin/sh

env=printenv;

mkdir -p ../etc
exec > ../etc/conf.properties
echo '#RabbitMQ connection details'
$env | grep rabbitHost
$env | grep rabbitSendQueue
$env | grep rabbitReceiveQueue
$env | grep rabbitUser
$env | grep rabbitPassword
echo 
echo '#MongoDB connection details'
$env | grep mongoHost
$env | grep mongoPort

exec > ../lib/pid
java -jar ../lib/FrontEnd.jar & echo $!
