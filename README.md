# Payment-Service (Production)
A Spring Boot microservice implementing the **Transactional Outbox** pattern for reliable event publishing. It ensures data consistency between the database and Kafka events among microservices.

<p align="left">
  <img src="https://go-skill-icons.vercel.app/api/icons?i=java,spring,postgresql,kafka,docker,gradle,postman,prometheus,git"/>
</p>

# Features

- **Transactional Outbox Pattern**: Guarantees reliable event delivery by persisting events in the database within the same transaction as business data changes, and then publishing them to Kafka asynchronously
- **Data Storage with PostgreSQL**: Uses PostgreSQL for structured transactional data (e.g., payments, user credentials) to ensure strong consistency
- **Event-Driven Architecture**: Publishes payment-related events to Kafka, enabling decoupled communication among microservices

# Technology Stack

- **Spring Boot** – Framework for building production-ready microservices
- **Kafka** – Event streaming platform for asynchronous messaging
- **PostgreSQL** – Relational database for transactional data storage
- **Docker** – Containerization for easy deployment
- **Liquibase** – Database version control and migration management
- **Gradle** – Build and dependency management tool
- **Postman** – API testing and validation
- **Prometheus** – Application metrics and monitoring
- **Git** – Version control system.

# Prerequisites

- **Java Development Kit (JDK)**: Version 17 or higher
- **Gradle**: For project build and dependency management
- **Docker**: For building and running containers
- **Vault**: For secure storage of API keys, JWT tokens, and sensitive data

```sh
java --version
gradle --version
docker --version
