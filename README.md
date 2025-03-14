# SMS Gateway Platform

## Overview
A scalable SMS processing platform built with **Spring Boot, Kafka, and PostgreSQL**, handling authentication, message queues, and telecom integration.

This project consists of two microservices that work together to process and send SMS messages:

1. **SMS Processing Platform** – Handles SMS requests, authentication, Kafka processing, and status updates.
2. **Telecom Operator Service** – Integrates with external telecom APIs, manages retries, and ensures SMS delivery.

Both services are structured under a single repository for easy cloning and management.

---

## Architecture
```
 sms-gateway-platform/
 │── sms-processing-platform/
 │   ├── src/
 │   ├── pom.xml
 │   ├── Dockerfile
 │
 │── telecom-operator-service/
 │   ├── src/
 │   ├── pom.xml
 │   ├── Dockerfile
 │
 │── docker-compose.yml
 │── README.md
 │── .gitignore
```

---

## Tech Stack
- **Java 17**
- **Spring Boot 3.3**
- **Kafka**
- **PostgreSQL**
- **Docker & Docker Compose**

---

## Cloning the Repository
To clone both services together, run:
```sh
git clone https://github.com/HNLad/sms-gateway-platform.git
```

---

## Setup & Installation
### 1. Install Dependencies
Ensure you have **Java 17**, **Maven**, **Kafka** and **Docker** installed.

### 2. Running with Docker Compose
To start both services:
```sh
docker-compose up --build
```
This will spin up Kafka, PostgreSQL, and both services.

### 3. Running Manually
#### Start Kafka & PostgreSQL
Run Kafka and PostgreSQL before starting services.

#### Start SMS Processing Platform
```sh
cd sms-processing-platform
mvn clean install
java -jar target/sms-processing-platform.jar
```

#### Start Telecom Operator Service
```sh
cd telecom-operator-service
mvn clean install
java -jar target/telecom-operator-service.jar
```

---

## API Endpoints
### SMS Processing Platform
- **Receive SMS Request**
  ```
  POST /telco/sendmsg
  Params: username, password, mobile, message
  ```
- **Kafka Consumer Processing**
  ```
  Internal processing via Kafka
  ```

### Telecom Operator Service
- **Send SMS to Telecom Operator**
  ```
  GET /telco/smsc?account_id={0}&mobile={1}&message={2}
  ```

---

## Contributing
1. Fork the repository.
2. Create a new feature branch.
3. Commit and push changes.
4. Open a Pull Request.

---

## License
