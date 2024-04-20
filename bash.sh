while true; do
  curl --silent https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6 | jq .message
done