
# Params

change the quarkus.datasource properties to point to your database
change the quarkus.http.port property to the port you want to use (dont forget to change the dockerfile as well)

change the kafka.bootstrap.servers property to point to your kafka broker
change the kafka.bootstrap.servers property on both AvResultProducer and APilotProducer

At /usr/local/kafka/config/server.properties of each EC2 instance, change the following properties:
```
#zookeeper connectivity (one per EC2 VM of this cluster)
zookeeper.connect=ec2-54-90-57-82.compute-1.amazonaws.com:2181,ec2-54-173-171-63.compute-
1.amazonaws.com:2181,ec2-54-236-47-54.compute-1.amazonaws.com:2181
```

And at /usr/local/zookeeper/conf/zoo.cfg
```
server.1=ec2-54-90-57-82.compute-1.amazonaws.com:2888:3888
server.2=ec2-54-173-171-63.compute-1.amazonaws.com:2888:3888
server.3=ec2-54-236-47-54.compute-1.amazonaws.com:2888:3888
```

# Testing

To test some of the funmcionality you can use the curl commands shown in the test.sh script

--- 

# Docker workflow

sudo docker pull diogorlopes/avaasie:latest

sudo docker build -f src/main/Docker/Dockerfile.jvm -t diogorlopes/avaasie .

sudo docker run -i --rm -p 8088:8088 diogorlopes/avaasie

sudo docker restart diogorlopes/avaasie

# Simulator

java -jar target/AVaaSSimulator.jar --broker-list ec2-18-205-28-33.compute-1.amazonaws.com:9092 --throughput 25 --filterprefix "av-event"

---

# DB connection

In order to connect the RDS database to the EC2 instance, you need to create a security group for the RDS instance and add the EC2 instance to the security group and vice versa.
Theres already that option directly in the Amazon Dashboard. 

Make sure to check the inbound and outbound rules for the security group.

To create the database schema you can do it directly in the EC2 instance with the following command and script:

(`sudo yum install mariadb` if you dont have mysql cli tool installed)
mysql -h database-avaas.cdqfozbwe56d.us-east-1.rds.amazonaws.com -P 3306 -u admin -p

paste the mysql_script into the command line

---
# Use Cases

## User subscribing/unsubscribing to AvaaS
 - User subscribes to AvaaS bia the User Subscription Service
 - Its managed by an Employee
 - 2 endpoints: 
    - POST subscription/user/
    - DELETE unsubscribe/{id}
    
## AV manufacturer entering/removing/updating to AVaaS catalog
 - Car manufacturer is responsible to manage the Av catalog
 - Car manufacturer enters a new av to the catalog
 - Car manufacturer updates a av in the catalog
 - Car manufacturer removes a av from the catalog
 - 3 endpoints:
    - POST manufacturer/service/enter/av/
    - PUT manufacturer/service/update/av/{id}/{model}
    - DELETE manufacturer/service/remove/av/{id}

## APILOT developer entering/removing/updating to AVaaS catalog
 - APILOT developer is responsible to manage the APilot catalog
 - APILOT developer enters a new apilot to the catalog
 - APILOT developer updates a apilot in the catalog
 - APILOT developer removes a apilot from the catalog
 - 3 endpoints:
    - POST apilot_dev/service/enter/apilot/
    - PUT apilot_dev/service/update/apilot/model/{id}/{model}
    - DELETE apilot_dev/service/remove/apilot/{id}

## User buying/selling a av
 - User buys a car by sending a request to the Av Kafka Resource with a valid av id
 - User sells a car by sending a request to the Av Kafka Resource with 0 av id
 - These requests are processed by Kafka and then forwarded to the corresponding consumers
 - 1 endpoint:
    - POST kafka/produce/av/

 ## User buying/selling a apilot
 - User buys a apilot by sending a request to the Apilot Kafka Resource with a valid apilot id and valid av id
 - User sells a apilot by sending a request to the Apilot Kafka Resource with 0 apilot id and valid av id
 - These requests are processed by Kafka and then forwarded to the corresponding consumers
 - 1 endpoint:
    - POST kafka/produce/apilot/

---

# System Design choices
 - As of right now is desgined to separate the resources from the services, so that the resources are \
 separated from the business logic and the services are responsible for the business logic
 - From our testing there was no difference in performance from using more partitions in the kafka topics
 right now, but to allow for more parallelism in the future we decided to use 5 partitions for each topic
 - The kafka topics are designed to be used by multiple consumers, so that we can scale the system horizontally
 