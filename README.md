# My-Bank
This is a service for “banking” operations. 
There are users in our system, each client has strictly one “bank account”, which initially contains some amount. 
Money can be transferred between clients. Interest is also accrued on the funds.
Access to the API is authenticated.

This is RESTful API that works with the Postgres database.

## Content
- [Stack](#stack)
- [Usage](#usage)
- [Sources](#sources)

## Stack
- Java 17
- Spring Boot 3
- Maven
- Postgres:14-alpine
- REST API
- Spring Data JPA

## Usage
- Download this repository
- Execute the commands
```sh
mvn clean install
```
```sh
docker-compose build
```
```sh
docker-compose up
```
- The service is available at: http://localhost:8080.

## Sources
This project is made according to these requirements: https://docs.google.com/document/d/13J22IQqssm0C8cC86kf5q154h5sqbVDl4MU6M8pHQp8/edit#heading=h.winp3ov600bm 
