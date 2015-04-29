#!/bin/sh

export rabbitHost=192.168.56.102;

export frRabbitSendQueue=commands;
export scRabbitSendQueue=rawResults;
export scRabbitReceiveQueue=commands;
export coRabbitSendQueue=processedResults;
export coRabbitReceiveQueue=rawResults;
export prRabbitReceiveQueue=processedResults;

export rabbitUser=bogdan;
export rabbitPassword=constantin;
export mongoHost=192.168.56.101;
export mongoPort=27017
