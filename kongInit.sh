#!/bin/bash

KONG_SERVER_ADDRESS=ec2-54-209-172-113.compute-1.amazonaws.com #update values
QUARKUS_URL_0=http://ec2-52-91-8-54.compute-1.amazonaws.com:8080/ #update values
QUARKUS_URL_1=http://ec2-52-91-8-54.compute-1.amazonaws.com:8080/ #update values
QUARKUS_URL_2=http://ec2-52-91-8-54.compute-1.amazonaws.com:8080/ #update values
QUARKUS_URL_3=http://ec2-52-91-8-54.compute-1.amazonaws.com:8080/ #update values

echo $KONG_SERVER_ADDRESS

################################### APILOT ################################################
curl -i -X POST \
--url "http://${KONG_SERVER_ADDRESS}:8001/services/" \
--data "name=invoke-apilot-service" \
--data "url=${QUARKUS_URL_0}"\

##########################routes
#apilot enter
curl -i -X POST \
--url "http://${KONG_SERVER_ADDRESS}:8001/services/invoke-apilot-service/routes" \
--data-urlencode "paths[]=~/apilot_dev_service/enter" 
#apilot remove
curl -i -X DELETE \
--url "http://${KONG_SERVER_ADDRESS}:8001/services/invoke-apilot-service/routes" \
--data-urlencode "paths[]=~/apilot_dev_service/remove/(?<id>\d+)" \
--data "strip_path=false"
#apilot update
curl -i -X PUT \
--url "http://${KONG_SERVER_ADDRESS}:8001/services/invoke-apilot-service/routes" \
--data-urlencode "paths[]=~/apilot_dev_service/update/(?<id>\d+)/(?<model>\S+)" \
--data "strip_path=false"

#######################invoke the services
#apilot enter
curl -v -i -X POST -H "accept: application/json" \
--url "http://${KONG_SERVER_ADDRESS}:8000/apilot_dev_service/enter" 
#apilot remove
curl -v -i -X PUT -H "accept: application/json" \
--url "http://${KONG_SERVER_ADDRESS}:8000/apilot_dev_service/remove/1" 
#apilot update
curl -v -i -X PUT -H "accept: application/json" \
--url "http://${KONG_SERVER_ADDRESS}:8000/apilot_dev_service/update/1/axm" 

############################### MANUFACTURER ###############################################
curl -i -X POST \
--url "http://${KONG_SERVER_ADDRESS}:8001/services/" \
--data "name=invoke-manufacturer-service" \
--data "url=${QUARKUS_URL_1}"\

################################routes
#enter
curl -i -X POST \
--url "http://${KONG_SERVER_ADDRESS}:8001/services/invoke-manufacturer-service/routes" \
--data-urlencode "paths[]=~/manufacturer_service/enter" 
#remove
curl -i -X DELETE \
--url "http://${KONG_SERVER_ADDRESS}:8001/services/invoke-manufacturer-service/routes" \
--data-urlencode "paths[]=~/manufacturer_service/remove/(?<id>\d+)" \
--data "strip_path=false"
#update
curl -i -X PUT \
--url "http://${KONG_SERVER_ADDRESS}:8001/services/invoke-manufacturer-service/routes" \
--data-urlencode "paths[]=~/manufacturer_service/update/(?<id>\d+)/(?<model>\S+)" \
--data "strip_path=false"

###############################invoke the services
#enter
curl -v -i -X POST -H "accept: application/json" \
--url "http://${KONG_SERVER_ADDRESS}:8000/manufacturer_service/enter" 
#remove
curl -v -i -X PUT -H "accept: application/json" \
--url "http://${KONG_SERVER_ADDRESS}:8000/manufacturer_service/remove/1" 
#update
curl -v -i -X PUT -H "accept: application/json" \
--url "http://${KONG_SERVER_ADDRESS}:8000/apilot_dev_service/update/1/axm" 

###################################PURCHASE##################################################
curl -i -X POST \
--url "http://${KONG_SERVER_ADDRESS}:8001/services/" \
--data "name=invoke-purchase-service" \
--data "url=${QUARKUS_URL_2}"\

#####################################routes
#av buy
curl -i -X POST \
--url "http://${KONG_SERVER_ADDRESS}:8001/services/invoke-purchase-service/routes" \
--data-urlencode "paths[]=~/purchase_service/av/buy"
#av sell
curl -i -X PUT \
--url "http://${KONG_SERVER_ADDRESS}:8001/services/invoke-purchase-service/routes" \
--data-urlencode "paths[]=~/purchase_service/av/sell/(?<id>\d+)" \
--data "strip_path=false"
#apilot select
curl -i -X PUT \
--url "http://${KONG_SERVER_ADDRESS}:8001/services/invoke-purchase-service/routes" \
--data-urlencode "paths[]=~/purchase_service/apilot/select/(?<id>\d+)/(?<apilotId>\d+)" \
--data "strip_path=false"
#apilot unselect
curl -i -X PUT \
--url "http://${KONG_SERVER_ADDRESS}:8001/services/invoke-purchase-service/routes" \
--data-urlencode "paths[]=~/purchase_service/apilot/unselect/(?<id>\d+)" \
--data "strip_path=false"

##################################invoke the services
#av buy
curl -v -i -X POST -H "accept: application/json" \
--url "http://${KONG_SERVER_ADDRESS}:8000/purchase_service/av/buy"
#av sell
curl -v -i -X PUT -H "accept: application/json" \
--url "http://${KONG_SERVER_ADDRESS}:8000/purchase_service/av/sell/1" 
#apilot select
curl -v -i -X PUT -H "accept: application/json" \
--url "http://${KONG_SERVER_ADDRESS}:8000/purchase_service/apilot/select/1/1" 
#apilot unselect
curl -v -i -X PUT -H "accept: application/json" \
--url "http://${KONG_SERVER_ADDRESS}:8000/purchase_service/apilot/unselect/1" 

######################################SUBSCRIPTION#########################################
curl -i -X POST \
--url "http://${KONG_SERVER_ADDRESS}:8001/services/" \
--data "name=invoke-subscribe-service" \
--data "url=${QUARKUS_URL_3}"\

######################################routes
#subscribe
curl -i -X POST \
--url "http://${KONG_SERVER_ADDRESS}:8001/services/invoke-unsubscribe-service/routes" \
--data-urlencode "paths[]=~/subscription"
#unsubscribe
curl -i -X DELETE \
--url "http://${KONG_SERVER_ADDRESS}:8001/services/invoke-unsubscribe-service/routes" \
--data-urlencode "paths[]=~/subscription/unsubcribe/(?<id>\d+)" \
--data "strip_path=false"

#####################################invoke the services
#subscribe
curl -v -i -X POST -H "accept: application/json" \
--url "http://${KONG_SERVER_ADDRESS}:8000/subscription"
#unsubscribe
curl -v -i -X PUT -H "accept: application/json" \
--url "http://${KONG_SERVER_ADDRESS}:8000/subscription/unsubcribe/1"