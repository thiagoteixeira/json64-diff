


# Json64-diff
Spring Boot application for JSON base64 binary data comparison.

## Travis Builds
| Type          | Status                                                   | Description                    |
|:--------------|:---------------------------------------------------------|:-------------------------------|
| Maven         | [![Build Status](https://travis-ci.org/thiagoteixeira/json64-diff.svg?branch=master)](https://travis-ci.org/thiagoteixeira/json64-diff)              | mvn clean install sonar:sonar |

## Analysis of Json64-diff project

[![sonar-quality-gate][sonar-quality-gate]][sonar-url]
[![sonar-coverage][sonar-coverage]][sonar-url]
[![sonar-bugs][sonar-bugs]][sonar-url]
[![sonar-vulnerabilities][sonar-vulnerabilities]][sonar-url]


## Development Environment
### Build Requirements
- [JDK10](http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html) or higher
- [Maven 3.5.2](https://maven.apache.org/download.cgi) or higher

### Run Unit and Integration Tests
`mvn test`

### Run Spring Boot Application
`mvn spring-boot:run` 



[sonar-url]:https://sonarcloud.io/dashboard?id=com.thiagojavabr%3Ajson64-diff
[sonar-quality-gate]: https://sonarcloud.io/api/project_badges/measure?project=com.thiagojavabr%3Ajson64-diff&metric=alert_status
[sonar-coverage]: https://sonarcloud.io/api/project_badges/measure?project=com.thiagojavabr%3Ajson64-diff&metric=coverage
[sonar-bugs]: https://sonarcloud.io/api/project_badges/measure?project=com.thiagojavabr%3Ajson64-diff&metric=bugs
[sonar-vulnerabilities]: https://sonarcloud.io/api/project_badges/measure?project=com.thiagojavabr%3Ajson64-diff&metric=vulnerabilities

