# Microshop

Microshop is a sample microservices application based on Spring technologies
(Boot 2, Framework 5, Cloud, Data, etc).

## Services

The technical services:
- `config`:
  - Config server, centralizes the configuration of the other services
  - Spring Boot app on port `8888` by default

The business services:
- `stock`:
  - Articles stock
  - Uses an embedded MongoDB database
  - Spring Boot app on port `8081` by default
- `order`:
  - Purchase orders
  - Uses an embedded H2 database
  - Spring Boot app on port `8082` by default

## Execution

Build all modules with `mvn package` from the root folder,
then execute each module with `mvn spring-boot:run` from its folder
using this order: first `microshop-config`, then the application services.

## Next steps

Not yet implemented:
- Registry
- Gateway
- Circuit breaker
- Distributed tracing
- Spring Boot Admin
- Security
