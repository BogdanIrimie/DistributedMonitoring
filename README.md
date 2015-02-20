# DistributedMonitoring
A distributed monitoring system for cloud

# Set up

Install RabbitMQ server (http://www.rabbitmq.com/download.html)

Install MongoDB (http://www.mongodb.org/downloads)

Install NMap (https://nmap.org/download.html)

(optional) Install mViewer to inspect database (https://github.com/Imaginea/mViewer)

(testing) Install RESTHEART for rest communication with MongoDB https://github.com/SoftInstigate/RESTHeart/releases

#Use cases & examples

Scan target for open ports: nmap \<ip\>

ex: nmap info.uvt.ro

Scan a class of ips: nmap \<ipClass\>
ex: nmap 192.168.1.0/24 

Scan for OS detection
ex: nmap -A info.uvt.ro

# About
This application is intended to be used as a distributed monitoring tool in order to obtain data regardng the state of the network and audit VMs security.
