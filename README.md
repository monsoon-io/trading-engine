# Paper Trading Engine

A backend system that simulates a stock exchange order matching engine. Built as a portfolio project targeting fintech engineering roles, demonstrating backend skills including concurrency, financial data integrity, and modern Java/Spring practices.

The engine supports price-time priority order matching — the same core mechanism used by real exchanges like NSE and NASDAQ — wrapped in a full backend: REST API, PostgreSQL persistence, user accounts with cash balances and stock holdings, and a simulated market data feed.

> **Status**: Phase 0 complete (project skeleton, database connectivity, health check). Actively being built phase by phase.

---

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 17 | Core language |
| Spring Boot | 4.1 | Application framework |
| Maven | 3.x | Build and dependency management |
| PostgreSQL | 18 | Persistence (via NeonDB for dev) |
| Spring Data JPA + Hibernate | - | ORM and database access |
| Spring Boot Actuator | - | Health check and monitoring |
| JUnit 5 + MockMvc | - | Testing |

---

## Prerequisites

- Java 17 or higher installed (`java -version` to check)
- Maven 3.x installed (`mvn -version` to check), or use the included `mvnw` wrapper
- A [NeonDB](https://neon.tech) account with a project created (free tier is sufficient)

---

## Configuration

This project uses environment variables for database credentials. No secrets are hardcoded or committed to the repository.

Copy `.env.example` from the project root and use it as a reference for the three required variables:

```
DB_URL       — JDBC connection URL for your Postgres database
DB_USERNAME  — Database username
DB_PASSWORD  — Database password
```

Your `DB_URL` must follow the JDBC format:
```
jdbc:postgresql://hostname/dbname?sslmode=require
```
Not the raw Postgres format (`postgresql://...`) — the `jdbc:` prefix is required.

### Setting environment variables in IntelliJ IDEA

1. Open **Run → Edit Configurations**
2. Select the `EngineApplication` configuration
3. Find the **Environment variables** field and add the three variables above
4. Repeat for the test configuration if running tests locally

---

## Running the Application

Using the Maven wrapper (no Maven installation required):

```bash
./mvnw spring-boot:run        # Linux / macOS
.\mvnw.cmd spring-boot:run    # Windows PowerShell
```

Or directly from IntelliJ: open `EngineApplication.java` and click the green run arrow.

---

## Verifying It Works

Once the app starts, hit the health endpoint:

```
GET http://localhost:8080/actuator/health
```

Expected response:

```json
{"groups":["liveness","readiness"],"status":"UP"}
```

`"status": "UP"` confirms both the web server and the database connection are healthy.

---

## Running Tests

```bash
.\mvnw.cmd test    # Windows PowerShell
./mvnw test        # Linux / macOS
```

Tests require the same three environment variables set in the test Run Configuration in IntelliJ (separate from the application Run Configuration).

---

## Project Structure

```
src/main/java/com/trading/engine/
├── EngineApplication.java   — Application entry point
├── config/                  — Spring configuration beans
├── controller/              — REST controllers
├── domain/                  — Core business objects (Order, Trade, Account, etc.)
├── matching/                — Order matching engine logic
└── repository/              — Spring Data JPA repositories
```

---

## Roadmap

| Phase | Description | Status |
|---|---|---|
| 0 | Project skeleton, DB connectivity, health check | ✅ Complete |
| 1 | Core domain model and order status state machine | 🔄 Next |
| 2 | In-memory order matching engine (price-time priority) | ⬜ Pending |
| 3 | REST API (place, cancel, query orders) | ⬜ Pending |
| 4 | PostgreSQL persistence with Flyway migrations | ⬜ Pending |
| 5 | Accounts, portfolios, buying-power checks | ⬜ Pending |
| 6 | Simulated market data feed + WebSocket streaming | ⬜ Pending |
| 7 | Cross-cutting hardening (logging, error handling) | ⬜ Pending |
| 8 | Testing, coverage, CI pipeline | ⬜ Pending |
| 9 | Deployment, observability, metrics | ⬜ Pending |
