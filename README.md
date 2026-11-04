# AI-Powered Fitness Microservices Application

> **Repository status: documentation scaffold.** This repository currently contains project documentation and structure placeholders, not a verified runnable implementation. Add and test the actual application source before describing it as complete.

## Overview
An AI-powered fitness application concept organized around User, Activity, and AI Recommendation services. The project scope described for this repository includes service discovery with Eureka, centralized configuration, Spring Cloud Gateway, OAuth 2.0 authentication with Keycloak, RabbitMQ event processing, and fitness recommendations generated using the Google Gemini API. The described data layer uses MySQL and MongoDB, with a React and Redux frontend.

## Planned capabilities
- User, Activity, and AI Recommendation microservices.
- Service discovery with Netflix Eureka.
- Centralized configuration with Spring Cloud Config Server.
- API routing through Spring Cloud Gateway.
- OAuth 2.0 authentication using Keycloak.
- Asynchronous activity-event processing with RabbitMQ.
- AI-generated fitness recommendations using the Google Gemini API.
- MySQL and MongoDB persistence.
- React and Redux frontend.

## Technology stack
**Backend:** Java, Spring Boot, Spring Cloud  
**Service infrastructure:** Eureka, Config Server, Spring Cloud Gateway  
**Security:** OAuth 2.0, Keycloak  
**Messaging / AI:** RabbitMQ, Google Gemini API  
**Databases:** MySQL, MongoDB  
**Frontend:** React, Redux

## Repository structure
```text
ai-fitness-microservices/
├── services/
│   ├── user-service/
│   ├── activity-service/
│   └── ai-recommendation-service/
├── infrastructure/
│   ├── service-registry/
│   ├── config-server/
│   └── api-gateway/
├── frontend/
├── docs/
│   ├── architecture.md
│   └── api-documentation.md
└── .github/ISSUE_TEMPLATE/
```

The directories are placeholders for the corresponding real modules. Populate them only with verified source code and configuration.

## Documentation
- [Architecture overview](docs/architecture.md)
- [API documentation template](docs/api-documentation.md)
- [User service notes](services/user-service/README.md)
- [Activity service notes](services/activity-service/README.md)
- [AI recommendation service notes](services/ai-recommendation-service/README.md)
- [Infrastructure notes](infrastructure/)

## Setup
Verified build commands, service ports, startup order, database initialization, and frontend instructions will be added after the actual source code and configuration are committed.

## Security
Keep Gemini API keys, Keycloak client secrets, database credentials, broker credentials, and access tokens out of Git. Provide sanitized example configuration and document required environment variables without real values.

## Project checklist
- [x] Repository documentation and structure scaffold
- [ ] Add actual Spring Boot microservice source code
- [ ] Add and verify Eureka, Config Server, and Gateway configuration
- [ ] Add Keycloak / OAuth 2.0 configuration
- [ ] Add RabbitMQ exchanges, queues, and event definitions
- [ ] Add Gemini integration and safe example configuration
- [ ] Add React / Redux source code
- [ ] Add tests, verified API documentation, and run instructions

## Note
The architecture and feature descriptions are planning documentation based on the project summary. They do not imply that the integrations are already implemented or runnable.