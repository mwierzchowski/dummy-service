Dummy Service
=============
![CI/CD](https://github.com/mwierzchowski/dummy-service/workflows/CI/CD/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=mwierzchowski_dummy-service&metric=alert_status)](https://sonarcloud.io/dashboard?id=mwierzchowski_dummy-service)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=mwierzchowski_dummy-service&metric=ncloc)](https://sonarcloud.io/dashboard?id=mwierzchowski_dummy-service)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=mwierzchowski_dummy-service&metric=coverage)](https://sonarcloud.io/dashboard?id=mwierzchowski_dummy-service)

> TODO replace badges!

This is a template project for [Spring Boot](https://spring.io/projects/spring-boot) based microservices. It's goal is
to simplify starting a new project with popular features ready out of the box and nothing more. Since this project is
simplified, without additional changes it is rather intended for internal (not public API) or hobby projects. Template
covers also CI/CD workflows and integration with a quality scan.

### Bill of Materials
Project template is composed of the following tools:
- [Gradle](https://gradle.org)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html)
- [Spring Boot Admin](https://github.com/codecentric/spring-boot-admin)
- [Spring MVC](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Data Redis](https://spring.io/projects/spring-data-redis)
- [Java Validation Framework (JSR 380)](https://beanvalidation.org/2.0-jsr380/)
- [Lombok](https://projectlombok.org)
- [MapStruct](https://mapstruct.org)
- [ShedLock](https://github.com/lukas-krecan/ShedLock)
- [Resilience4J](https://github.com/resilience4j/resilience4j)
- [PostgreSQL](https://www.postgresql.org)
- [Redis](https://redislabs.com)
- [Springdoc-OpenAPI](https://springdoc.org)
- [OpenAPI Generator](https://github.com/OpenAPITools/openapi-generator)
- [Liquibase](https://www.liquibase.org)
- [Spock Framework](http://spockframework.org)
- [WireMock](http://wiremock.org)
- [Testcontainers](https://www.testcontainers.org) and [Playtika](https://github.com/Playtika/testcontainers-spring-boot)
- [GitHub Actions](https://github.com/features/actions)
- [SonarCloud](https://sonarcloud.io)
- [Docker](https://www.docker.com) 

For the full list of libraries/dependencies see the [build.gradle](build.gradle).

### Code Standards
Project's structure, naming convention and to some degree design were inspired by:
- [Onion](https://www.codeguru.com/csharp/csharp/cs_misc/designtechniques/understanding-onion-architecture.html) / 
  [Clean](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) /
  [Hexagonal](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software)) Architecture
- [Domain Driven Design (DDD)](https://en.wikipedia.org/wiki/Domain-driven_design)

If one wants to read more about combining all these together, 
[here](https://herbertograca.com/2017/11/16/explicit-architecture-01-ddd-hexagonal-onion-clean-cqrs-how-i-put-it-all-together/)
is an interesting article.

But Why?
--------

One may ask: *Why not to use [Spring Initializr](https://start.spring.io) or [JHipster](https://www.jhipster.tech)
instead of the template?* Well, there are few reasons...
1. I am really picky when it comes to code standards (see above). Initializr and JHipster are both great tools
   (actually, this project was initially started with Initializr) but after using them I always find myself
   correcting tons of small *imperfections* here and there. 
1. Initializr does not support [Spock](http://spockframework.org) which is my favourite tests framework.
1. JHipster generates lots of *super-must-have-cloud-code* (after all, its hipster stuff ;-)). It is great when you try
   to spin a startup, but in many cases it is superfluous for small - internal or hobby - projects.
1. Both tools do not cover CI/CD workflows.

Another interesting question: *Why not to use one of [zillion](https://github.com/search?q=spring+boot+template)
(actually, around 2-3k) template projects available on the GitHub?* Indeed, some of them could be close to my needs
but...
1. I started one of my hobby projects as monolith and just later decided to cut it into microservice pieces. So I had
   a need for a template similar as much as possible to the original project's structure to avoid extra effort.
1. Creating this template was an interesting exercise.
1. Did I mention I am picky? Even good templates do not cover all of my requirements.
   
Prerequisites
-------------

Project requires following components being installed on developer's machine:  

Tool                                                                              | Minimal Version    | Comment
----------------------------------------------------------------------------------|--------------------|---------------------------------------------------------------------------
[Git](https://git-scm.com/)                                                       | `latest`           | 
[JDK](https://adoptopenjdk.net/archive.html?variant=openjdk14&jvmVariant=hotspot) | `15`               | AdoptOpenJDK is recommended. Newer versions may have problems with Groovy! 
[Docker Desktop](https://www.docker.com/products/docker-desktop)                  | `2.4`              |
[IntelliJ IDEA](https://www.jetbrains.com/idea/)                                  | `latest`           | Ultimate version would be helpful but it is not required

Actually, **IntelliJ IDEA is only recommended**. Feel free to replace it with [Eclipse](https://www.eclipse.org) or
Notepad :) However, if one decides to use IntelliJ, following plugins will be vey handy (Eclipse versions should be
available too):

Plugin                                                                    | Comment
--------------------------------------------------------------------------|----------------------------------------------------------------------
[Lombok](https://plugins.jetbrains.com/plugin/6317-lombok)                | Support for Lombok generated code
[MapStruct](https://plugins.jetbrains.com/plugin/10036-mapstruct-support) | Support for MapStruct generated code
[SonarLint](https://plugins.jetbrains.com/plugin/7973-sonarlint)          | Quality feedback on the fly 
[Docker](https://plugins.jetbrains.com/plugin/7724-docker)                | Support for docker-compose (handy when starting application locally)

**Please note:** References to generated code (e.g. Lombok properties or MapStruct mappers) in most current IDEs is
highlighted as errors if one does not have installed corresponding plugins.

Setup
-----

### Create project

**Please note:** One should already have GitHub account.

1. [Create a new repository from this template](https://docs.github.com/en/free-pro-team@latest/github/creating-cloning-and-archiving-repositories/creating-a-repository-from-a-template).
1. Import/clone project to IDE (when asked, enable all suggested supports/toolkits, e.g. annotation processing).
1. Change application's root package and application main class (`DummyApplication`).
1. Change application group and description in `build.gradle` (both marked with TODO flags)
1. Check if there are no TODO flags left in the project code.
1. Reload Gradle project.
1. Change README.md (at least project name)
1. Change license (if needed).
1. Push changes to GitHub.

### SonarCloud Integration

**Please note:** One should already have [SonarCloud](https://sonarcloud.io) account authorized as an application in
GitHub. Additionally, CI/CD script assumes that one logs to SonarCloud with GitHub account. 

1. Add [new project analyze](https://sonarcloud.io/projects/create) in SonarCloud (choose *GitHub Actions* as
   an analysis method).
1. Copy SonarCloud token and add to GitHub repository secrets as `SONAR_TOKEN`.

### DockerHub Integration

**Please note:** One should already have [DockerHub](https://hub.docker.com) account. Additionally, CI/CD script
assumes that both GitHub and DockerHub share the same account id.

1. Create new DockerHub [repository](https://hub.docker.com/repositories).
1. Add DockerHub password to GitHub repository secrets as `DOCKERHUB_PASSWORD`.

Usage
-----

Project build is powered by Gradle (wrapper included). Most build tasks are provided by `java`, `spring-boot` and
`docker-compose` plugins. If one is not familiar with those plugins (and their tasks) please check corresponding
documentation. Most useful tasks:

Task          | Description
--------------|----------------------------------------------------------------------------------------------
`clean`       | Cleans the build
`test`        | Executes unit and integration tests
`build`       | Builds the application (with tests execution)
`composeUp`   | Starts local development environment (e.g. RDBMS, Swagger UI) as Docker Compose services
`composeDown` | Stops local development environment (all the data is wiped, including database content)
`bootRun`     | Builds and starts application (requires local development environment to be up and running)  

For example, following command starts application after a clean build:
```
./gradlew clean composeUp bootRun 
```

Development Environment
-----------------------

Local development environment is provided with the code by Docker Compose. Once started, following services are
available on the localhost with the preconfigured accounts:
- [pgAdmin](http://localhost:81) (`admin@localhost` / `admin`) 
- [Spring Boot Admin](http://localhost:82) (`admin` / `admin`)
- [Swagger UI](http://localhost:83/swagger)

License
-------
This software is released under the [MIT](LICENSE) Open Source license.
