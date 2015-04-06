# DistributedMonitoring

We propose a fault tolerant distributed system based on Nmap, and possible other tools, for scanning target hosts and networks. The system will also store the result for later retrieval, audit and statistics purposes.

## Set up

Install RabbitMQ server (http://www.rabbitmq.com/download.html)

Install MongoDB (http://www.mongodb.org/downloads)

Install NMap (https://nmap.org/download.html)

Install JRE 1.8 (http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)

## Configuration

Each of the four components (FrontEnd, Scanner, Converter and Presenter) has their own configuration file (conf.properties). The configuration file contains the ip and port for MongoDB and the ip, queue names and credentials for RabbitMQ.

##Use cases & examples

Scan target for open ports: nmap \<ip\>

ex: nmap http://scanme.nmap.org/

Scan a class of ips: nmap \<ipClass\>
ex: nmap 192.168.1.0/24 

Scan for OS detection
ex: nmap -A http://scanme.nmap.org

##Interacting with the monitoring system

Jobs can be submited by making a HTTP request:
```
http://<ip>:8000/job?request=<clientRequest>
```

clientResponse is a JSON with the following fields:
* `clientId` - the id the client provide, it can be used to retrieve all the jobs for a client
* `command`- the command that will be executed by the system
* `responseAddress` - is the address where the results will be sent
* `processors` - used for filtering and processing of the raw results
* `adapter` - used for adapting the results for a specific system

ex:
```json
curl -G "http://localhost:8000/job" --data-urlencode 'request=
{
	"clientId" : "13",
	"command" : "nmap 192.168.56.105",
	"responseAddress" : "http://192.168.56.101:8008/jobFinished",
	"processors" :["processors.XmlToJsonConverter","processors.TlsFilter"],
	"adapter" : "processors.EventHubAdapter"
}
```


