# Microshop

Microshop is a sample microservices application based on Spring technologies
such as Boot 2, Framework 5, Cloud (Config, Netflix, Gateway), Data.

## Features

Currently implemented :
- Two basic business services
- Common utility module
- Config server
- Registry
- Gateway
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
  - Admin interface for Spring Boot applications
  - Spring Boot app on port `8090` by default, based on Spring Boot Admin

The business services:
- `microshop-stock`:
  - Articles stock
  - Uses an embedded MongoDB database
  - Spring Boot app on port `8081` by default
- `microshop-order`:
  - Purchase orders
  - Uses an embedded H2 database
  - Spring Boot app on port `8082` by default

Other modules:
- `microshop-common`:
  - Common utility classes

## Execution

Build all modules with `mvn package` from the root folder,
then execute each module with `mvn spring-boot:run` from its folder
using this order from first to last: `microshop-config`, `microshop-registry`,
business services, `microshop-gateway`, `microshop-admin`

## Next steps

Not yet implemented:
- Client load balancing
- Circuit breaker
- Distributed tracing
- Hot configuration reload
- Technical services security
- Business services security
