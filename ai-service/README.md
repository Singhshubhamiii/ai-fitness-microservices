# AI Recommendation Service
Consumes activity JSON messages from Kafka topic fitness.activity-events, generates general guidance using Gemini, and persists recommendations in MongoDB.
- GET /api/recommendations/{activityId}
- Configure GEMINI_API_KEY and GEMINI_MODEL.
Local port: 8083. Without an API key the service stores clearly labelled generic demo guidance. AI output is informational, not medical advice.