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

