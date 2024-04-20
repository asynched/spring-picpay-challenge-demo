curl 'http://localhost:9001/v1/transactions' \
  -H 'Content-Type: application/json' \
  -d '{
    "payerId": "54be1fc6-d21f-480a-a220-eaa6af912f03",
    "payeeId": "844081f6-bb83-4ec9-858e-ac9a75f7b70d",
    "value": 100
  }'