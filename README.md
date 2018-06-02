


# Json64-diff
Spring Boot application for JSON base64 binary data comparison.

## Travis Builds
| Type          | Status                                                   | Description                    |
|:--------------|:---------------------------------------------------------|:-------------------------------|
| Maven         | [![Build Status](https://travis-ci.org/thiagoteixeira/json64-diff.svg?branch=master)](https://travis-ci.org/thiagoteixeira/json64-diff)              | mvn clean install sonar:sonar |

## SonarCloud analysis of Json64-diff project

[![sonar-quality-gate][sonar-quality-gate]][sonar-url]
[![sonar-coverage][sonar-coverage]][sonar-url]
[![sonar-bugs][sonar-bugs]][sonar-url]
[![sonar-vulnerabilities][sonar-vulnerabilities]][sonar-url]


## Development Environment
### Build Requirements
- [JDK 10](http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html) or higher
- [Maven 3.5.2](https://maven.apache.org/download.cgi) or higher

### Run Unit and Integration Tests
`mvn test`

### Run Spring Boot Application 
`mvn spring-boot:run`

## Rest API Swagger Documentation

  - [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
  
  Note: the above example uses a local web container on port 8080. 

## Request & Response Examples

### API Resources

  - [POST /v1/diff/[id]/left](#post-v1diffidleft)
  - [POST /v1/diff/[id]/right](#post-v1diffidright)
  - [GET /v1/diff/[id]](#get-v1diffid)

### POST /v1/diff/[id]/left

Example: http://localhost:8080/v1/diff/1/left

Request headers: `Content-Type:"application/json"`

Request body: (JSON base64 encoded binary data)
    
    eyAibmFtZSI6IlRoaWFnbyBUZWl4ZWlyYSIgfQ==
    
Note: real JSON value is `{ "name":"Thiago Teixeira" }`

Response body:

    {
        "id": 1,
        "left": "eyAibmFtZSI6IlRoaWFnbyBUZWl4ZWlyYSIgfQ==",
        "right": null
    } 
    
### POST /v1/diff/[id]/right

Example: http://localhost:8080/v1/diff/1/right

Request headers: `Content-Type:"application/json"`

Request body: (JSON base64 encoded binary data)
    
    eyAibmFtZSI6IlRoaWFnbyBUZWl4ZWlyYSIgfQ==
    
Note: real JSON value is `{ "name":"Thiago Teixeira" }`

Response body:

    {
        "id": 1,
        "left": "eyAibmFtZSI6IlRoaWFnbyBUZWl4ZWlyYSIgfQ==",
        "right": "eyAibmFtZSI6IlRoaWFnbyBUZWl4ZWlyYSIgfQ=="
    }

### GET /v1/diff/[id]

Example: http://localhost:8080/v1/diff/1

Response body:

    {
        "message": "The JSON contents are equal!"
    }


[sonar-url]:https://sonarcloud.io/dashboard?id=com.thiagojavabr%3Ajson64-diff&nocache
[sonar-quality-gate]: https://sonarcloud.io/api/project_badges/measure?project=com.thiagojavabr%3Ajson64-diff&metric=alert_status
[sonar-coverage]: https://sonarcloud.io/api/project_badges/measure?project=com.thiagojavabr%3Ajson64-diff&metric=coverage&cached=1
[sonar-bugs]: https://sonarcloud.io/api/project_badges/measure?project=com.thiagojavabr%3Ajson64-diff&metric=bugs
[sonar-vulnerabilities]: https://sonarcloud.io/api/project_badges/measure?project=com.thiagojavabr%3Ajson64-diff&metric=vulnerabilities

