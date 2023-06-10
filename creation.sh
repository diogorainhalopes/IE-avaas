#!/bin/bash

echo "Starting Terraform creation script..."
echo "Seting up ZooKeeper..."
cd
sudo wget https://dlcdn.apache.org/zookeeper/zookeeper-3.8.0/apache-zookeeper-3.8.0-bin.tar.gz
sudo tar -zxf apache-zookeeper-3.8.0-bin.tar.gz
sudo mv apache-zookeeper-3.8.0-bin /usr/local/zookeeper
sudo mkdir -p /var/lib/zookeeper
echo "tickTime=2000
dataDir=/var/lib/zookeeper
clientPort=2181" > /usr/local/zookeeper/conf/zoo.cfg
echo "Done!"

echo "Seting up Java..."
sudo yum -y install java-1.8.0-openjdk.x86_64
echo "Done!"

echo "Seting up Kafka..."
sudo /usr/local/zookeeper/bin/zkServer.sh start
sudo wget https://downloads.apache.org/kafka/3.3.2/kafka_2.13-3.3.2.tgz
sudo tar -zxf kafka_2.13-3.3.2.tgz
sudo mv kafka_2.13-3.3.2 /usr/local/kafka
sudo mkdir /tmp/kafka-logs
echo "Done!"

echo "Retrieving Hostname..."
ip=`curl http://169.254.169.254/latest/meta-data/public-hostname`

echo "Setting up Kafka Server config..."
sudo sed -i "s/#listeners=PLAINTEXT:\/\/:9092/listeners=PLAINTEXT:\/\/$ip:9092/g" /usr/local/kafka/config/server.properties
# sudo sed -i "s/#listeners=PLAINTEXT:\/\/:9092/listeners=PLAINTEXT:\/\/ec2-44-202-70-152.compute-1.amazonaws.com:9092/g" /usr/local/kafka/config/server.properties

sudo sed -i "s/num.partitions=1/num.partitions=5/g" /usr/local/kafka/config/server.properties

# due to AWS network stablishment process, check if 90 seconds is enough for your situation
(sleep 90 && sudo /usr/local/kafka/bin/kafka-server-start.sh -daemon /usr/local/kafka/config/server.properties )&


echo "Setting Docker..."
sudo yum install -y docker
sudo service docker start
sudo docker login -u diogorlopes -p <PASSWORD>

sudo docker pull diogorlopes/avaasie:latest
sudo docker run -i --rm -p 8088:8088 diogorlopes/avaasie
echo "All Done!"