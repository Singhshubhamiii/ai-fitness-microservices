# Activity Service
Spring Boot REST API for fitness activity records. MongoDB persistence and Kafka event publication on topic fitness.activity-events.
- GET /api/activities
- GET /api/activities?userId={id}
- POST /api/activities with userId, type, durationMinutes and caloriesBurned.
Local port: 8082. Configure MONGODB_URI, KAFKA_BOOTSTRAP_SERVERS and EUREKA_URL.