echo "Testing microservices..."

echo "Testing apilot_dev..."

echo '{ "company": "APILOTDEV" }'
curl -X 'POST' \
  'http://localhost:8088/apilot_dev' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{ "company": "APILOTDEV" }'

echo "Testing av kafka resource..."
echo '{ "id": 335, "brand": "UMM", "model": "Alter II" }'
curl -X 'POST' \
  'http://localhost:8088/kafka/produce/av' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{ "id": 335, "brand": "UMM", "model": "Alter II" }'

echo '{ "id": 234, "brand": "Ford", "model": "Focus" }'
curl -X 'POST' \
  'http://localhost:8088/kafka/produce/av' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{ "id": 234, "brand": "Ford", "model": "Focus" }'


echo "Testing apilot kafka resource..."
echo '{ "avid": 335, "apilotid": 1 }'
curl -X 'POST' \
  'http://localhost:8088/kafka/produce/apilot' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{ "avid": 335, "apilotid": 1 }'


echo 'Testing employee...'
echo '{ "eid": 56, "ename": "Bob" }'
curl -X 'POST' \
  'http://localhost:8088/employee' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{ "eid": 56, "ename": "Bob" }'


echo '{ "eid": 57, "ename": "Alice" }'
curl -X 'POST' \
  'http://localhost:8088/employee' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{ "eid": 57, "ename": "Alice" }'