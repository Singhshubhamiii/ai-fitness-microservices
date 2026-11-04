# React Fitness Dashboard

Vite + React interface for creating activity records and retrieving AI recommendations.

## Run
```bash
npm install
cp .env.example .env
npm run dev
```
Open http://localhost:5173. Start MongoDB, Kafka, Activity Service (8082) and AI Service (8083) first. The Activity Service allows the local Vite origin through CORS. Configure `VITE_ACTIVITY_API` and `VITE_AI_API` for different local addresses.

The UI currently supports activity creation/listing and recommendation lookup. It does not yet include user registration, Keycloak login, or Redux state management.
