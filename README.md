# Task_Management_API
A Spring Boot RESTful API for managing tasks with JWT-based authentication, per-user task CRUD, rate limiting, and PostgreSQL persistence.

## **Features**
- **User Authentication:** Register & login with JWT.
- **Task Management:** Create, update, delete, and list tasks for authenticated users only.
- **Task Properties:** id, title, description, status (todo, in-progress, done), createdAt, updatedAt.
- **Rate Limiting:**
  - Unauthenticated endpoints: 3 requests/min per IP
  - Authenticated endpoints: 10 requests/min per user
- **Environment Configuration:** Database credentials, JWT secret, and expiration via environment variables.
- **Error Handling:** Standardized JSON error responses with proper HTTP status codes.
- **Database:** PostgreSQL with Flyway migrations.
- **Server Port:** Default 9876.

## **Tech Stack**
- Backend: Java 17, Spring Boot
- Security: Spring Security, JWT
- Database: PostgreSQL, Spring Data JPA
- Migrations: Flyway
- Validation: Hibernate Validator
- Rate Limiting: Bucket4j

## Getting Started
## Prerequisites
- Java 17+
- PostgreSQL running locally or remotely
- Maven or IntelliJ with Maven support
- Postman (for testing)

## Steps
1. Clone repository
   git clone https://github.com/Rohith-Parambath/Task_Management_API.git
   cd task-management-api
2. Create .env file (see .env.sample [reference]()
3. Run the application
   ./mvnw spring-boot:run
   - The server will start on http://localhost:9876.

## Enviroment Variable
DB_HOST=localhost

DB_PORT=5432

DB_NAME=todo_db

DB_USER=todo_user

DB_PASSWORD=secret_password

JWT_SECRET=ReplaceWithStrongRandomString

JWT_EXPIRATION_MS=3600000

SERVER_PORT=9876

- JWT_EXPIRATION_MS is in milliseconds (e.g., 3600000ms = 1 hour).

## Database Setup & Migrations
- Flyway automatically applies migrations at application startup.
- Migration file: src/main/resources/db/migration/V1__create_users_and_tasks.sql
- Tables created:
  - users (id, username, email, password, created_at, updated_at)
  - tasks (id, user_id, title, description, status, created_at, updated_at)
- tasks.user_id has a foreign key to users.id.

## API Endpoints
### Authentication
| Method | Endpoint   | Body / Headers                  | Description                     |
|--------|------------|---------------------------------|---------------------------------|
| POST   | /register  | `{ "username": "...", "email": "...", "password": "..." }` | Register a new user             |
| POST   | /login     | `{ "username" or "email": "...", "password": "..." }`       | Login and get JWT               |









