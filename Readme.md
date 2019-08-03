
```
docker run -p 8086:8086 -p 8083:8083 \
    -e INFLUXDB_ADMIN_ENABLED=true \
    influxdb:1.5-alpine

curl -G http://localhost:8086/query --data-urlencode "q=CREATE DATABASE io"
curl -G http://localhost:8086/query\?pretty\=true --data-urlencode "db=io" --data-urlencode "q=select * from system_cpu_usage "
```
