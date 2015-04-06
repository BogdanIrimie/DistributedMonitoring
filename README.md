# DistributedMonitoring

We propose a fault tolerant distributed system based on Nmap, and possible other tools, for scanning target hosts and networks. The system will also store the result for later retrieval, audit and statistics purposes.

## Set up

Install RabbitMQ server (http://www.rabbitmq.com/download.html)

Install MongoDB (http://www.mongodb.org/downloads)

Install NMap (https://nmap.org/download.html)

Install JRE 1.8 (http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)

## Configuration

The four components (FrontEnd, Scanner, Converter and Presenter) 

##Use cases & examples

Scan target for open ports: nmap \<ip\>

ex: nmap info.uvt.ro

Scan a class of ips: nmap \<ipClass\>
ex: nmap 192.168.1.0/24 

Scan for OS detection
ex: nmap -A info.uvt.ro

## About
This application is intended to be used as a distributed monitoring tool in order to obtain data regardng the state of the network and audit VMs security.
