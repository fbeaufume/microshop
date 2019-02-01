# Microshop

Microshop is a sample microservices application based on Spring technologies
(Boot 2, Framework 5, Cloud, Data, etc).

## Services

The technical services:
- `microshop-config`:
  - Config server, centralizes the configuration of the other services
  - Spring Boot app on port `8888` by default
- `microshop-registry`:
  - Registry server, records instances of business services
  - Spring Boot app on port `8761` by default

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
using this order: first `microshop-config`, then `microshop-registry` then the application services.

## Next steps

Not yet implemented:
- Client load balancing
- Circuit breaker
- Gateway
- Distributed tracing
- Spring Boot Admin
- Security
