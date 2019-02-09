# Microshop

Microshop is a sample microservices application based on Spring technologies
such as Boot 2, Framework 5, Cloud (Config, Netflix, Gateway), Data.

Follows the "config first" (rather than "registry first") approach.
Meaning that the business services first connect to the config server
to get the location of the service registry, then connect to the service
registry instead of the other way around.

Requires Java 8+.

## Features

Currently implemented :
- Two basic business services
- Common utility module
- Config server
- Service registry
- Gateway
- Client load balancing
- Circuit breaker
- Distributed tracing
- Spring Boot Admin

## Services

The technical services:
- `microshop-config`:
  - Config server, centralizes the configuration of the other services
  - Spring Boot app on port `8888` by default, based on Spring Cloud Config
- `microshop-registry`:
  - Registry server, records instances of business services
  - Spring Boot app on port `8761` by default, based on Spring Cloud Netflix Eureka
- `microshop-gateway`:
  - Gateway, routes requests to business services
  - Spring Boot app on port `8080` by default, based on Spring Cloud Gateway
- `microshop-admin`:
  - Optional admin interface for Spring Boot applications
  - Spring Boot app on port `8090` by default, based on Spring Boot Admin

The business services:
- `microshop-stock`:
  - Articles stock, used by the order service
  - Uses an embedded in-memory MongoDB database that is reset each time the service starts
  - Spring Boot app on port `8081` by default
- `microshop-order`:
  - Purchase orders, uses the stock service
  - Uses an embedded in-memory H2 database that is reset each time the service starts
  - Spring Boot app on port `8082` by default

Other modules:
- `microshop-common`:
  - Common utility classes

The business URL are available through the gateway, i.e. http://localhost:8080/ :

Service | Method | Path | Description
--------|--------|------|------------
Stock   | GET    | /stock/api/articles | Get all articles
Stock   | GET    | /stock/api/articles/{id} | Get an article
Stock   | PUT    | /stock/api/articles | Update an article
Order   | GET    | /order/api/orders | Get all orders
Order   | GET    | /order/api/orders/{id} | Get an order
Order   | POST   | /order/api/orders | Create a new order if enough articles in stock

## Execution

Build all modules with `mvn package` from the root folder,
then execute each module with `mvn spring-boot:run` (or `java -jar target/<service>.jar`)
from its folder using this order from first to last: `microshop-config`,
`microshop-registry`, business services, `microshop-gateway`, `microshop-admin`.

To launch additional business service instances change the HTTP using for example
`mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8083`
(or `java -jar target/<service>.jar --server.port=8083`).

## Next steps

Not yet implemented:
- Hot configuration reload
- Technical services security
- Business services security
