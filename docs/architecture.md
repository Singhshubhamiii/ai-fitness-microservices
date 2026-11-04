# Architecture

```mermaid
flowchart TB
  Client[React / Redux frontend - planned] --> Gateway[Spring Cloud Gateway]
  Gateway --> Keycloak[Keycloak OAuth2 / JWT]
  Gateway --> User[User Service]
  Gateway --> Activity[Activity Service]
  Activity -->|fitness.activity-events| Kafka[(Kafka)]
  Kafka --> AI[AI Recommendation Service]
  AI --> Gemini[Google Gemini API]
  User --> MySQL[(MySQL)]
  Activity --> Mongo[(MongoDB)]
  AI --> Mongo
  Registry[Eureka Registry]
  Config[Spring Cloud Config Server]
  Gateway -. discovery .-> Registry
  User -. discovery .-> Registry
  Activity -. discovery .-> Registry
  AI -. discovery .-> Registry
```

## Data ownership
- User Service: MySQL/JPA.
- Activity Service: MongoDB and Kafka event producer.
- AI Service: Kafka event consumer, Gemini integration and MongoDB recommendations.
- API Gateway: route discovery and JWT validation.
- Eureka: service registration and discovery.
- Config Server: native filesystem configuration profile.

## Operational notes
The diagram shows the intended local development architecture. Keycloak realm/client provisioning and issuer claims must be configured before authenticated Gateway requests succeed. Direct service ports are exposed for local testing; protect them with service authentication and network policies in deployed environments. Kafka retry/DLT, event idempotency, distributed tracing and production secrets management remain future hardening work.
