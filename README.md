# Microshop

Microshop is a sample microservices application based on Spring technologies
such as Boot 2, Framework 5, Cloud (Config, Netflix, Gateway, etc), Data
and others.

Requires Java 8+ and Maven 3.5+.

## Features

Currently implemented :
- Two basic business services with an in-memory database (H2 and MongoDB)
- Common utility module with logs of slow HTTP requests
- Config server, based on Spring Cloud Config
- Service registry, based on Spring Cloud Netflix Eureka
- API gateway, based on Spring Cloud Gateway
- Client load balancing, based on Spring Cloud Netflix Ribbon
- Circuit breaker, based on Spring Cloud Netflix Hystrix
- Circuit breaker dashboard, based on Spring Cloud Netflix Hystrix Dashboard
- Distributed tracing, based on Spring Cloud Zipkin and Zipkin server
- Configurable tracing of business methods
- Admin interface, based on Spring Boot Admin

## Services

The business services:
- `microshop-order`:
  - Purchase orders, uses the stock service
  - Uses an embedded in-memory H2 database that is reset each time the service starts
  - Spring Boot app on port `8081` by default
- `microshop-stock`:
  - Articles stock, used by the order service
  - Uses an embedded in-memory MongoDB database that is reset each time the service starts
  - Spring Boot app on port `8082` by default

The technical services:
- `microshop-config`:
  - Config server, centralizes the configuration of the other services
  - Spring Boot app on port `8888` by default, based on Spring Cloud Config
- `microshop-registry`:
  - Registry server, records instances of business services
  - Spring Boot app on port `8761` by default, based on Spring Cloud Netflix Eureka
- `microshop-gateway`:
  - API gateway, routes requests to business services
  - Spring Boot app on port `8080` by default, based on Spring Cloud Gateway
- `microshop-admin`:
  - Optional admin interface for Spring Boot applications
  - Spring Boot app on port `8090` by default, based on Spring Boot Admin
- `microshop-dashboard`:
  - Optional Hystrix dashboard, displays circuit breakers stats
  - Spring Boot app on port `7979` by default, based on Spring Cloud Netflix Hystrix Dashboard
- `microshop-zipkin`:
  - Optional Zipkin UI, displays distributed traces
  - Spring Boot app on port `9411` by default, based on Zipkin server

Other modules:
- `microshop-common`:
  - Common utility classes

The rest of this document omits `microshop-`.

Internal dependencies:

Service   | Dependencies
----------|--------------
common    | -
config    | -
registry  | config (run)
order     | common (build), config (run), registry (run), zipkin (run)
stock     | common (build), config (run), registry (run), zipkin (run)
gateway   | config (run), registry (run), zipkin (run)
admin     | config (run), registry (run)
dashboard | -
zipkin    | registry (run)

`build` means that this a build time dependency, `run` means that this is called
(HTTP or other) during the service execution.

## Execution

The application follows the "config first" (rather than "registry first") approach.
Meaning that the business services first connect to the config server
to get the location of the service registry, then connect to the service
registry.

Build all modules with `mvn package` from the root folder,
then execute each module with `mvn spring-boot:run` (or `java -jar target/<service>.jar`)
from its folder, using this order from first to last: `config` then
`registry` then the rest in any order.

To launch additional business service instances change the HTTP using for example
`mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8083`
(or `java -jar target/<service>.jar --server.port=8083`).

The business URL are:

Service | Method | Path                                    | Description
--------|--------|-----------------------------------------|------------
Both    | GET    | http://localhost:8080/api/reset         | Reset all business data
Order   | GET    | http://localhost:8080/api/orders        | Get all orders
Order   | GET    | http://localhost:8080/api/orders/{id}   | Get an order
Order   | POST   | http://localhost:8080/api/orders        | Create a new order and update the stock
Stock   | GET    | http://localhost:8080/api/articles      | Get all articles
Stock   | GET    | http://localhost:8080/api/articles/{id} | Get an article
Stock   | PUT    | http://localhost:8080/api/articles      | Update an article

In addition the technical URL are:

URL                                     | Description
----------------------------------------|------------
http://localhost:8888/order/default     | Configuration of Order
http://localhost:8761/                  | Service registry UI
http://localhost:8761/eureka/apps/order | Service registry records of Order
http://localhost:8090/                  | Spring Boot Admin UI
http://localhost:7979/hystrix           | Hystrix Dashboard, then use http://localhost:8081/actuator/hystrix.stream
http://localhost:9411/zipkin            | Zipkin UI

A Postman configuration file is also provided for convenience in the `postman` folder.

## Next steps

Not yet implemented:
- Replace Ribbon by Spring Cloud Loadbalancer,
  see https://spring.io/blog/2018/12/12/spring-cloud-greenwich-rc1-available-now
  and https://piotrminkowski.wordpress.com/2019/04/05/the-future-of-spring-cloud-microservices-after-netflix-era/
- Replace Hystrix by Resilience4j, see previous links
- Hot configuration reload
- Technical services security
- Business services security
