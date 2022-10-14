# Spring-Webflux
For the purposes of studies, it is a simple application with Spring WebFlux. It is a simple CRUD involving Books.

## Reactive App

WebFlux is used to alleviate inefficient resource utilization by retrieving resources that would otherwise be idle while waiting for I/O activity.

## MongoDB

Unlike JDBC drivers which are blocking, MongoDB gives better interaction as it is a reactive NoSQL, and it has a big impact on performance.

## Docker and Docker-Compose

Docker is a good approach to create isolated instances. Docker-compose is a tool to define and run these instances.
To run Docker with the actual configuration, just enter the directory */mongodb* and simply run the command:
```bash
docker compose up
```


## Doc

[Spring WebFlux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)
[MongoDB](https://www.mongodb.com/docs/manual/)
[Docker-Compose](https://docs.docker.com/compose/)
