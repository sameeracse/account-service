# account service microservice

Account service microservice will list all accounts associated to a user and provide all transactions for a given account

## Requirements

For building and running the application you need:

 - JDK 11

# Build the application
 - ./gradlew clean build

## Running the application locally
 - ./gradlew bootrun

## Database
In memory H2 database is created with the application startup. Data file is located at _/src/main/resource/data.sql_

Once application is started , database can be accessed using H2 console http://localhost:9000/h2-console

Login  credentials 
```
    - database url : jdbc:h2:mem:test
    - username : user
    - password : password
```

## Api documentation
Json - http://localhost:9000/v3/api-docs/

YAML - http://localhost:9000/v3/api-docs.yaml

Swagger UI - http://localhost:9000/swagger-ui/index.html

## Actuator

Actuator endpoint : http://localhost:9000/actuator/health

## Testing Scripts

Use below curl commands to execute apis

#### getAccounts

```
curl -X GET \
  http://localhost:9000/accounts \
  -H 'authorization: 1' \
  -H 'cache-control: no-cache' \
  -H 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
  -H 'postman-token: f52b4cd9-1f97-1bfa-adbd-6bb8a2b26077' \
  -F from=123456 \
  -F to=abcdefg \
  -F amount=100
```

#### getAccountTransactions

```
curl -X GET \
  'http://localhost:9000/account/1/transactions?pageNo=0&pageSize=10' \
  -H 'authorization: 1' \
  -H 'cache-control: no-cache' \
  -H 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
  -H 'postman-token: bcc617b8-3da8-c302-2e75-109ec2e16bc6' \
  -F from=123456 \
  -F to=abcdefg \
  -F amount=100
```

