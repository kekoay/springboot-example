# Springboot Demo Project
This is to demonstrate the use of the following libraries and frameworks by
implementing a simple CRUD application for a User endponts.

- Springboot
- Flyway
- Hibernate
- Testcontainers

I was going to implement more like updating users, managing cars, and the renting cars. Though, 
I didn't even though in the db.migrations dir I have the sql schemas to create the relationships between 
the data objects. 

## Requirements
- Java 11
- Docker

## Build
```shell
gradlew clean build
```

## Test
```shell
gradlew test
```
* Note that you need to have docker running.

## Run
```shell
gradlew bootRun
```
* Need to have an instance of postgresql running.

## Todo
- Add more endpoints
- Create dockerfile for bundling application
