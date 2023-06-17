#!/bin/bash
idBrokerInt=$((idBroker))
if [ $idBrokerInt -ge 0 ] && [ $idBrokerInt -le 3 ]; then
        echo "Starting Terraform creation script..."
        sudo yum update -y
        sleep 20
        echo "Setting up ZooKeeper..."
        cd
        sudo wget https://dlcdn.apache.org/zookeeper/zookeeper-3.8.0/apache-zookeeper-3.8.0-bin.tar.gz
        sudo tar -zxf apache-zookeeper-3.8.0-bin.tar.gz
        sudo mv apache-zookeeper-3.8.0-bin /usr/local/zookeeper
        sudo mkdir -p /var/lib/zookeeper
        echo "tickTime=2000
        dataDir=/var/lib/zookeeper
        clientPort=2181
        maxClientCnxns=60
        initLimit=10
        syncLimit=5" > /usr/local/zookeeper/conf/zoo.cfg
        echo "Done!"

        echo "Setting up Java..."
        sudo yum -y install java-1.8.0-openjdk.x86_64
        echo "Done!"

        echo ${idBroker} > /var/lib/zookeeper/myid

        echo "Setting up Kafka..."
        sudo /usr/local/zookeeper/bin/zkServer.sh start
        sudo wget https://downloads.apache.org/kafka/3.3.2/kafka_2.13-3.3.2.tgz
        sudo tar -zxf kafka_2.13-3.3.2.tgz
        sudo mv kafka_2.13-3.3.2 /usr/local/kafka
        sudo mkdir /tmp/kafka-logs
        echo "Done!"

        ip=$(curl http://169.254.169.254/latest/meta-data/public-hostname)
        sudo sed -i "s/#listeners=PLAINTEXT:\/\/:9092/listeners=PLAINTEXT:\/\/$ip:9092/g" /usr/local/kafka/config/server.properties
        sudo sed -i "s/broker.id=0/broker.id=${idBroker}/g" /usr/local/kafka/config/server.properties
        sudo sed -i "s/offsets.topic.replication.factor=1/offsets.topic.replication.factor=${totalBrokers}/g" /usr/local/kafka/config/server.properties
        sudo sed -i "s/transaction.state.log.replication.factor=1/transaction.state.log.replication.factor=${totalBrokers}/g" /usr/local/kafka/config/server.properties
        sudo sed -i "s/transaction.state.log.min.isr=1/transaction.state.log.min.isr=${totalBrokers}/g" /usr/local/kafka/config/server.properties

        # Due to AWS network establishment process, check if 90 seconds is enough for your situation
        sleep 90 && sudo /usr/local/kafka/bin/kafka-server-start.sh -daemon /usr/local/kafka/config/server.properties

        echo "Setting Docker..."
        sudo yum install -y docker
        sudo service docker start
        sudo usermod -aG docker ec2-user

        sudo docker pull diogorlopes/avaasie:latest
        sudo docker run -i --rm -p 8088:8088 diogorlopes/avaasie

elif [ $idBrokerInt -eq 4 ]; then
        echo "Setting Camunda..."
        sudo yum install -y docker
        sudo service docker start
        sudo usermod -aG docker ec2-user

        docker pull camunda/camunda-bpm-platform:latest
        docker run -d --name camunda -p 8080:8080 camunda/camunda-bpm-platform:latest

elif [ $idBrokerInt -eq 5 ]; then
        echo "Setting up KONG..."
        sudo yum install -y docker
        sudo service docker start
        sudo usermod -aG docker ec2-user
        sudo docker network create kong-net
        sudo docker run -d --name kong-database \
            --network=kong-net \
            -p 5432:5432 \
            -e "POSTGRES_USER=kong" \
            -e "POSTGRES_DB=kong" \
            -e "POSTGRES_PASSWORD=kongpass" \
            postgres:13
        sudo docker run --rm --network=kong-net \
            -e "KONG_DATABASE=postgres" \
            -e "KONG_PG_HOST=kong-database" \
            -e "KONG_PG_PASSWORD=kongpass" \
            kong:3.1.1 kong migrations bootstrap
        sudo docker run -d --name kong-gateway \
            --network=kong-net \
            -e "KONG_DATABASE=postgres" \
            -e "KONG_PG_HOST=kong-database" \
            -e "KONG_PG_USER=kong" \
            -e "KONG_PG_PASSWORD=kongpass" \
            -e "KONG_PROXY_ACCESS_LOG=/dev/stdout" \
            -e "KONG_ADMIN_ACCESS_LOG=/dev/stdout" \
            -e "KONG_PROXY_ERROR_LOG=/dev/stderr" \
            -e "KONG_ADMIN_ERROR_LOG=/dev/stderr" \
            -e "KONG_ADMIN_LISTEN=0.0.0.0:8001, 0.0.0.0:8444 ssl" \
            -p 8000:8000 \
            -p 8443:8443 \
            -p 8001:8001 \
            -p 127.0.0.1:8444:8444 \
            kong:3.1.1

elif [ $idBrokerInt -eq 6 ]; then
        echo "Setting up KONGA..."
        sudo docker pull pantsel/konga
        sudo docker run -d --name konga -p 1337:1337 pantsel/konga
else
    echo "Invalid idBroker value. Please specify a valid option."
fi
echo "All Done!"
