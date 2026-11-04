# API Documentation

## User Service (8081)
- `GET /api/users` — list user profiles.
- `GET /api/users/{id}` — fetch one profile.
- `POST /api/users` — create profile. Example: `{"name":"Alex","email":"alex@example.com","age":28}`.

## Activity Service (8082)
- `GET /api/activities` — list activities.
- `GET /api/activities?userId={id}` — filter by user.
- `POST /api/activities` — create activity. Example: `{"userId":"user-123","type":"RUNNING","durationMinutes":35,"caloriesBurned":280}`. Successful creation publishes a JSON event to Kafka topic `fitness.activity-events`.

## AI Recommendation Service (8083)
- `GET /api/recommendations/{activityId}` — retrieve recommendation. Returns 404 until event processing completes.

## API Gateway (8080)
- `/users/**` -> user-service
- `/activities/**` -> activity-service
- `/recommendations/**` -> ai-service

The Gateway validates JWTs against `KEYCLOAK_ISSUER_URI`. Configure a Keycloak realm and client before calling the gateway. Direct service ports are exposed for local development and should not be exposed publicly without service-level authentication and network controls.
