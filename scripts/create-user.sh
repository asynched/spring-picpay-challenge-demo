curl 'http://localhost:9001/v1/users' \
  -H 'Content-Type: application/json' \
  -d '{
    "name": "Eder Lima",
    "email": "eder@mail.com",
    "password": "password123",
    "cpf": "00100200300"
  }'
  # -d '{
  #   "name": "João Carlos",
  #   "email": "joao@mail.com",
  #   "password": "password123",
  #   "cpf": "00100200301"
  # }'
  