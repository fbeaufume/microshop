# Microshop

This is a sample microservices application based on Spring technologies (Boot, Framework, Data, Cloud, etc).

Modules:
- `microshop-stock`: articles stock, uses an embedded MongoDB database
- `microshop-order`: purchase orders, uses an embedded H2 database

Build with `mvn package` from the root folder, then execute with `mvn spring-boot:run` in each sub folder.

Not yet implemented:
- Config server
- Registry
- Gateway
- Circuit breaker
- Distributed tracing
- Spring Boot Admin
