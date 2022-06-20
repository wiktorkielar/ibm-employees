# IBM Employees

## Description

Simple web application with REST endpoints that allows to:

* retrieve list of all employees
* retrieve particular employee by UUID
* add a new employee
* modify existing employee

## Requirements

1. JDK 17
2. Git
3. Docker Desktop

## Building and Running without Docker

1. `git clone https://github.com/wiktorkielar/ibm-employees.git`
2. `./mvnw clean install`
3. `java -jar target/employees-0.0.1-SNAPSHOT.jar`

## Building and Running with Docker

1. `git clone https://github.com/wiktorkielar/ibm-employees.git`
2. `docker build -t employees:latest .`
3. `docker run -p 8080:8080 employees`

## Running Unit Tests

1. `./mvnw test`

## Using the application

1. Please refer to the file in the main directory and import it to Postman: `Employees.postman_collection.json`
2. Alternatively use cURL
    1. `curl --location --request GET 'http://localhost:8080/api/v1/employees' \
       --data-raw ''`
    2. `curl --location --request GET 'http://localhost:8080/api/v1/employees/{uuid}' \
       --data-raw ''`
    3. `curl --location --request POST 'http://localhost:8080/api/v1/employees/' \
       --header 'Content-Type: application/json' \
       --data-raw '{
       "firstName": "John",
       "lastName": "Doe",
       "jobRole": "Java Developer"
       }'`
    4. `curl --location --request PUT 'http://localhost:8080/api/v1/employees/{uuid}"' \
       --header 'Content-Type: application/json' \
       --data-raw '{
       "firstName": "Adam",
       "lastName": "Smith",
       "jobRole": "Go Developer"
       }'`