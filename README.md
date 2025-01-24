# Movie Recommendation Service

This project implements a movie recommendation system using a microservices architecture. It is designed to provide personalized movie suggestions to users based on their preferences and viewing history.

## Architecture Overview

The system is divided into the following services:

- **Movie Service**: Manages movie-related data, including details such as titles, genres, and descriptions.
- **User Service**: Handles user information, preferences, and viewing histories.
- **Recommendation Service**: Generates personalized movie recommendations by analyzing data from both the Movie and User services.

Each service is designed to operate independently, allowing for scalability and maintainability.

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)**: Ensure that JDK 11 or higher is installed.
- **Apache Maven**: Used for building and managing the project dependencies.
- **PostgreSQL**: Ensure that PostgreSQL is installed and a database is set up for the services.

### Installation

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/Kotik112/movie-recommendation-service.git
   cd movie-recommendation-service

2. Configure the `application.yaml` Files

For each service (`movie-service`, `user-service`, `recommendation-service`), create an `application.yaml` file in the `src/main/resources` directory with the following content:

```yaml
spring:
  application:
    name: your-service-name
  jpa:
    database: POSTGRESQL
    show-sql: true
    hibernate:
      ddl-auto: create-drop
  datasource:
    url: jdbc:postgresql://localhost:5432/your_database_name
    username: your_username
    password: your_password
    driver-class-name: org.postgresql.Driver

