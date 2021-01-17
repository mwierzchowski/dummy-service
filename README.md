Dummy Service
=============

> **TODO** - This section has to be updated in cloned project

This is a template project for [Spring Boot](https://spring.io/projects/spring-boot) based microservices. It's goal is
to simplify starting a new project with popular features ready out of the box and nothing more. Since this project is
simplified, without additional changes it is rather intended for internal (not public API) or hobby projects. Template
covers also CI/CD workflows and integration with a quality scan.

### But why?

> **TODO** - This section has to be removed in cloned project

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

### Bill of materials
Project is composed of the following tools:
- [Gradle](https://gradle.org)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html)
- [Spring Boot Dev Tools](https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-devtools)  
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

For the full list see the [build.gradle](build.gradle).

### Code standards
Project structure, naming convention and to some degree design were inspired by:
- [Onion](https://www.codeguru.com/csharp/csharp/cs_misc/designtechniques/understanding-onion-architecture.html) / 
  [Clean](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) /
  [Hexagonal](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software)) Architecture
- [Domain Driven Design (DDD)](https://en.wikipedia.org/wiki/Domain-driven_design)

If one wants to read more about combining all these together, 
[here](https://herbertograca.com/2017/11/16/explicit-architecture-01-ddd-hexagonal-onion-clean-cqrs-how-i-put-it-all-together/)
is an interesting article.
 
Prerequisites
-------------

Project requires following components being installed on a developer's machine:  

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

Project setup
------------

> **TODO** - This section has to be removed in cloned project

### Clone repository

**Please note:** One should already have GitHub account.

1. [Create a new repository from this template](https://docs.github.com/en/free-pro-team@latest/github/creating-cloning-and-archiving-repositories/creating-a-repository-from-a-template).
1. Import/clone project to IDE (when asked, enable all suggested supports/toolkits, e.g. annotation processing).
1. Update `build.gradle`
    1. Change application group and description.
    1. Reload Gradle project.
1. Update README
    1. Update or remove sections marked with TODO flag 
    1. Optional - add CI/CD badge to README (GitHub Actions / CICD Workflow / '3 dots button')
    1. Optional - change license.
1. Push changes to GitHub.

**Please note:** Later on, while working on actual service logic, rename application root package, main class and all
other *Dummy* classes/resources.

### SonarCloud integration

**Please note:** One should already have [SonarCloud](https://sonarcloud.io) account authorized as an application in
GitHub. Additionally, CI/CD script assumes that one logs to SonarCloud with GitHub account.

1. Add [new project analyze](https://sonarcloud.io/projects/create) in SonarCloud (choose *GitHub Actions* as
   an analysis method).
1. Copy SonarCloud token and add to GitHub repository secrets as `SONAR_TOKEN`.
1. Optional - add SonarCloud badges to README (SonarCloud project home page, lower right corner): 
    1. 'Quality Gate Status'
    1. 'Lines of Code'
    1. 'Coverage'
1. In SonarCloud define new code based on a previous version (Administration / New Code).
1. Push changes to GitHub. 

### DockerHub integration

**Please note:** One should already have [DockerHub](https://hub.docker.com) account. Additionally, CI/CD script
assumes that both GitHub and DockerHub share the same account id.

1. Create new DockerHub [repository](https://hub.docker.com/repositories).
1. Add DockerHub password to GitHub repository secrets as `DOCKERHUB_PASSWORD`.
1. Service image will be published to DockerHub on the next release.

How to run...
-------------

### Build

Project build is powered by [Gradle wrapper](https://gradle.org) with additional plugins (e.g. `java`, `spring-boot`,
`docker-compose`). Few most useful build tasks:
- `clean` - cleans the build
- `test` - executes unit and integration tests
- `build` - builds the application (and executes tests)

For example, following command runs a clean build:
```
./gradlew clean build 
```

### Dev tools

Development tools are provided as a code by Docker Compose. They may be controlled with standard docker commands
or using Gradle tasks:
- `composeUp` - starts development tools as Docker Compose services (waits until services are up and running)
- `composeDown` - stops development tools (all the data is wiped, including database content)

For example, following command starts development tools:
```
./gradlew composeUp 
```

Once started, following services are available:

Service                                                               | URL                         | Credentials
----------------------------------------------------------------------|-----------------------------|----------------------------
[PostgreSQL](https://www.postgresql.org)                              | http://localhost:5432       | `dev` / `dev`
[Redis](https://redislabs.com)                                        | http://localhost:6379       | N/A
[pgAdmin](https://www.pgadmin.org)                                    | http://localhost:81         | `admin@localhost` / `admin` 
[Spring Boot Admin](https://github.com/codecentric/spring-boot-admin) | http://localhost:82         | `admin` / `admin`
[Swagger UI](https://swagger.io/tools/swagger-ui/)                    | http://localhost:83/swagger | N/A 

### Application

Service, as a regular Spring Boot application may be started locally by running main application class or using Gradle
task:
- `bootRun` - starts application (compiles and builds code if needed)

Since application to start requires development tools to be up and running, one may combine Gradle tasks to launch
complete development environment with a single command:
```
./gradlew composeUp bootRun 
```

Once started, application listens on http://localhost:8080. Status of the running application can be checked using one
of the Actuator endpoints, e.g.:
- http://localhost:8080/actuator/info - general info
- http://localhost:8080/actuator/health - health status

Project includes [spring-boot-devtools](https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-devtools)
"*that can make the application development experience a little more pleasant*", e.g. provides code changes detection
and automatic restarts.

License
-------
This software is released under the [MIT](LICENSE) Open Source license.
