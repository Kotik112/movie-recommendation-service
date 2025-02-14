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
   ```

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
   
   server:
     port: <unique port number>
   
   logging:
     pattern:
       console: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
   ```
3. **Run the Services:**

   To run each service, navigate to its directory and use the Gradle Wrapper to execute the `bootRun` task. This approach ensures consistency across different environments.
   
   1. **Movie Service**:

   ```bash
   cd movie-service
   ./gradlew bootRun
   ```
   
    2. **User Service**:
    
    ```bash
   cd user-service
   ./gradlew bootRun
   ```
   
    3. **Recommendation Service**:
    
    ```bash
   cd recommendation-service
   ./gradlew bootRun
    ```

## Usage

Once all services are running, you can interact with them through their respective endpoints:

1. **Movie Service**:
   - **Base URL**: `http://localhost:8081`
   - **Endpoints**:
      - `GET /movies`: Retrieve a list of all movies.
      - `GET /movies/{movieId}`: Retrieve details of a specific movie by its ID.
      - `POST /movies`: Add a new movie.
      - `PUT /movies/{movieId}`: Update an existing movie by its ID.
      - `DELETE /movies/{movieId}`: Delete a movie by its ID.

2. **User Service**:
   - **Base URL**: `http://localhost:8082`
   - **Endpoints**:
      - `GET /users`: Retrieve a list of all users.
      - `GET /users/{userId}`: Retrieve details of a specific user by their ID.
      - `POST /users`: Add a new user.
      - `PUT /users/{userId}`: Update an existing user by their ID.
      - `DELETE /users/{userId}`: Delete a user by their ID.

3. **Recommendation Service**:
   - **Base URL**: `http://localhost:8083`
   - **Endpoints**:
      - `GET /recommendations/{userId}`: Retrieve movie recommendations for a specific user by their ID.

**Note**: Replace `{movieId}` and `{userId}` with the actual IDs when making requests.

**Example**: To get recommendations for a user with ID `123`, send a `GET` request to `http://localhost:8083/recommendations/123`.

You can use tools like [Postman](https://www.postman.com/) or `curl` to test these endpoints. For instance:

```bash
curl -X GET http://localhost:8081/movies


