
### Params

change the quarkus.datasource properties to point to your database

change the quarkus.http.port property to the port you want to use (dont forget to change the dockerfile as well)

change the kafka.bootstrap.servers property to point to your kafka broker


--- 
### Docker workflow

docker build -f src/main/Docker/Dockerfile.jvm -t diogorlopes/avaasie .

docker run -i --rm -p 8080:8080 diogorlopes/avaasie

docker restart diogorlopes/avaasie

sudo docker pull diogorlopes/avaasie:latest

---
# DB connection

In order to connect the RDS database to the EC2 instance, you need to create a security group for the RDS instance and add the EC2 instance to the security group and vice versa.
Theres already that option directly in the Amazon Dashboard. 

Make sure to check the inbound and outbound rules for the security group.

To create the database schema you can do it directly in the EC2 instance with the following command and script:

(`sudo yum install mariadb` if you dont have mysql cli tool installed)
mysql -h avaas-database-1.cienya3ax72e.us-east-1.rds.amazonaws.com -P 3306 -u root -p

paste the mysql_script into the command line

---
### Use Cases

#### User subscribing/unsubscribing to AvaaS
 - User subscribes to AvaaS bia the User Subscription Service
 - Its managed by an Employee
 - 2 endpoints: 
    - POST subscription/user/
    - DELETE unsubscribe/{id}
    
#### Car manufacturer entering/removing/updating to AVaaS catalog
 - Car manufacturer is responsible to manage the Av catalog
 - Car manufacturer enters a new av to the catalog
 - Car manufacturer updates a av in the catalog
 - Car manufacturer removes a av from the catalog
 - 3 endpoints:
    - POST manufacturer/service/enter/av/
    - PUT manufacturer/service/update/av/{id}/{model}
    - DELETE manufacturer/service/remove/av/{id}

#### APILOT developer entering/removing/updating to AVaaS catalog
 - APILOT developer is responsible to manage the APilot catalog
 - APILOT developer enters a new apilot to the catalog
 - APILOT developer updates a apilot in the catalog
 - APILOT developer removes a apilot from the catalog
 - 3 endpoints:
    - POST apilot_dev/service/enter/apilot/
    - PUT apilot_dev/service/update/apilot/model/{id}/{model}
    - DELETE apilot_dev/service/remove/apilot/{id}