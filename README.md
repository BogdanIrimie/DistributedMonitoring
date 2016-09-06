# DistributedMonitoring

We propose a fault tolerant distributed monitoring system based on Nmap, and possible other tools, for scanning target hosts and networks. The system will also store the result for later retrieval, audit and statistics purposes.

## Set up

### Requirements

Install RabbitMQ server - V3.5.3 or [newer](http://www.rabbitmq.com/download.html)

Install MongoDB - V2.6.3 or [newer](http://www.mongodb.org/downloads)

Install Nmap - V6.40 or [newer](https://nmap.org/download.html) on every machine that runs a `Scanner` component.

Install JRE [1.8](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) on all the machines that run one of the four components (FrontEnd, Scanner, Converter, Presenter).

### Building the project manually

Install [Maven](https://maven.apache.org/download.cgi)

Install [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

For every module (FrontEnd, Scanner, Converter and Presenter), navigate to the pom.xml and run mvn `mvn -Dmaven.test.skip=true package` (this will create the jar without running unit tests)

### Configuration

Each of the four components (FrontEnd, Scanner, Converter and Presenter) has their own configuration file (conf.properties). The configuration file contains the ip and port for MongoDB and the ip, queue names and credentials for RabbitMQ.

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

### Using the deployment

Deployment can be done via Chef. Recipes are provided in the deployment directory.
For test purposes, the chef client can be ran in local mode: `chef-client -z -r "recipe[dmon]"`


More info can be found on the [deployment page](https://github.com/IrimieBogdan/DistributedMonitoring/wiki/Deployment).

##Test/Stress the system

Fallow the guide on the [test page](https://github.com/IrimieBogdan/DistributedMonitoring/wiki/Performance-Testing).

##Interacting with the monitoring system

Jobs can be submitted by making a HTTP POST request `http://<ip>:8080/request` with a json body. More details about the request body can be found on [request model page](https://github.com/IrimieBogdan/DistributedMonitoring/wiki/Requests).
