# DistributedMonitoring

We propose a fault tolerant distributed monitoring system based on Nmap, and possible other tools, for scanning target hosts and networks. The system will also store the result for later retrieval, audit and statistics purposes.

Build Status of main branch: [![Build Status](https://travis-ci.org/IrimieBogdan/DistributedMonitoring.svg?branch=master)](https://travis-ci.org/IrimieBogdan/DistributedMonitoring)

## Set up

### Requirements

Install RabbitMQ server - V3.4.4 or newer (http://www.rabbitmq.com/download.html)

Install MongoDB - V2.6.3 or newer (http://www.mongodb.org/downloads)

Install Nmap - V6.40 or newer (https://nmap.org/download.html) on every machine that runs a `Scanner` component.

Install JRE 1.8 (http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) on all the machines that run one of the four components (FrontEnd, Scanner, Converter, Presenter).

### Building the project manually

Install Maven (https://maven.apache.org/download.cgi)

Install JDK 1.8 (http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

For every module (FrontEnd, Scanner, Converter and Presenter), navigate to the pom.xml and run mvn `mvn -Dmaven.test.skip=true package` (this will create the jar withought running unit tests)

### Configuration

Each of the four components (FrontEnd, Scanner, Converter and Presenter) has their own configuration file (conf.properties). The configuration file contains the ip and port for MongoDB and the ip, queue names and credentials for RabbitMQ.

### Using the deployment

As an alternative to manually building the project and creating the config files, the deployment directory can be used. The directory contain the latest stable jars and a directory structure that is easy to deploy and manage.
In order to use the depoyment the following environment variables should be set:

* `rabbitHost` IP of the machine hosting RabbitMQ (on all machines hosting `FrontEnd`, `Scanner`, `Converter` and `Presenter` components)
* `feRabbitSendQueue` name of the queue used to send messages by the `FrontEnd` component (on machines hosting FrontEnd components)
* `scRabbitSendQueue` name of the queue used to send messages by the `Scanner` component (on machines hosting Scanner components)
* `scRabbitReceiveQueue` name of the queue used to receive messages by the `Scanner` component (on machines hosting Scanner components)
* `coRabbitSendQueue` name of the queue used to send messages by thge `Converter` component (on machines hosting Converter components)
* `coRabbitReceiveQueue` name of the queue used to receive messages by the `Converter` component (on machines hosting Converter components)
* `prRabbitReceiveQueue` name of the queu used to receive messages by the `Presenter` component (on machines hosting Presenter components)
* `rabbitUser` credentials for RabbitMQ (on all machines hosting FrontEnd, Scanner, Converter and Presenter components)
* `rabbitPassword` credentials for RabbitMQ (on all machines hosting FrontEnd, Scanner, Converter and Presenter components)
* `mongoHost` IP of the machine hsoting MongoDB (on all machines hosting FrontEnd, Scanner, Converter and Presenter components)
* `mongoPort` port on which MongoDB is running (on all machines hosting FrontEnd, Scanner, Converter and Presenter components)

As the relation between components is `FrontEnd`-`Scanner`-`Converter`-`Presenter`, the pair: 
```
feRabbitSendQueue - scRabbitReceiveQueue; 
scRabbitSendQueue - coRabbitReceiveQueue; 
coRabbitSendQueue - prRabbitReceiveQueue;
```
should have identical names.

Configuration file example for the FrontEnd component:
```
#RabbitMQ connection details
rabbitHost = 192.168.56.102
rabbitSendQueue = commands
rabbitUser = rabbitUser
rabbitPassword = rabbitPassword

#MongoDB connection details
mongoHost = 192.168.56.101
mongoPort = 27017
```

If the environment variables are set, simply run `/bin/run.sh` for each component (this will create the configuration files and will start the component, more info can be found on https://github.com/IrimieBogdan/DistributedMonitoring/wiki/Deployment)

##Test/Stress the system

Fallow the guide on https://github.com/IrimieBogdan/DistributedMonitoring/wiki/Testing

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


##Examples

###HTTP request
```json
curl -G "http://localhost:8000/job" --data-urlencode 'request=
{
	"clientId" : "13",
	"command" : "nmap 192.168.56.105",
	"responseAddress" : "http://192.168.56.101:8008/jobFinished",
	"processors" :[
		"processors.XmlToJsonConverter",
		"processors.TlsCiphersuitesFilter",
		"processors.TlsEcrypt2Level"
	],
	"adapter" : "adapters.EventHubAdapter"
}
```

###Nmap commands

Scan target for open ports:`nmap http://scanme.nmap.org/`

Scan a class of ips: `nmap 192.168.1.0/24` 

Scan for OS detection: `nmap -A http://scanme.nmap.org`

Detect TLS ciphersuite: `nmap --script ssl-enum-ciphers -p 443 google.com`
