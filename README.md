
```markdown
# Property Booking System

This guide will walk you through the steps to set up and run the Property Booking System application.

## Prerequisites

- JDK (Java Development Kit) 17
- Maven
- Git
```

## Used Technologies

The Property Booking System is built using the following technologies:

- **Java**: The primary programming language used for developing the application.
- **Spring Boot**: A popular Java framework used to create stand-alone, production-grade Spring-based applications.
- **H2 Database**: An in-memory SQL database used for storing data during development and testing.
- **Flyway**: A database migration tool used to manage database schema changes and versioning.
- **JUnit**: A unit testing framework for Java used to write and run tests for the application.
- **Mockito**: A mocking framework for Java used to create and configure mock objects in unit tests.

These technologies work together to create a robust and scalable property booking system.


## Clone the Repository

```bash
git clone https://github.com/beshoygerges/AirbnbPropertyBooking.git
cd property-booking-system
```


## Build the Code

```bash
./mvnw clean package
```

## Run the Application

```bash
./mvnw spring-boot:run
```


```markdown
## Running the Application with Docker

You can run the Property Booking System application using Docker, which allows for easy deployment and management of containerized applications.

### Steps to Run the Application with Docker
```

1. **Build the Docker Image:**

```bash
docker build -t airbnb-property-booking .
```

This command builds a Docker image for the Property Booking System application.

2. **Run the Docker Container:**

```bash
docker run -p 8080:8080 airbnb-property-booking
```

This command runs the Docker container based on the previously built image, and it maps port 8080 of the container to port 8080 on the host machine.

Once the Docker container is running, you can access the application by navigating to `http://localhost:8080` in your web browser.

### Note:

- Make sure Docker is installed on your machine before running these commands.
- Ensure that no other process is using port 8080 on your host machine to avoid conflicts.
```

## Accessing the API

### 1- Postman Collection

You can import the provided Postman collection file to test the APIs:

#### Host & Stay.postman_collection.json

### 2- Swagger

Alternatively, you can access the API documentation using Swagger UI:

- Open a web browser
- Navigate to `http://localhost:8080/swagger-ui.html`

You can use either Postman or Swagger to interact with the API endpoints, make requests, and test the functionality of the application.

## Accessing H2 Database Console

The Property Booking System uses an H2 in-memory database for storing data. You can access the H2 database console to view the full data.

### Steps to Access H2 Database Console

1. Make sure the application is running.
2. Open a web browser.
3. Navigate to `http://localhost:8080/h2-console`.
4. Enter the following information:
    - **JDBC URL:** `jdbc:h2:mem:testdb`
    - **Username:** `sa`
    - **Password:** `password`
5. Click on the **Connect** button.

Once connected, you can browse the tables, execute SQL queries, and view the full data stored in the database.

To use the API endpoints, you'll need UUIDs for users, properties, and payment methods. Here are some example UUIDs:

### Users

1. User 1: `8cfa68a3-34cf-4da5-aa2f-88c49c31a688`
2. User 2: `e1c40b79-97cb-48e7-a03b-7f07725b0e86`
3. User 3: `452b2309-792f-4d94-91fd-6b374075f3f2`

### Properties

1. Property 1: `8cfa68a3-34cf-4da5-aa2f-88c49c31a689`
2. Property 2: `e1c40b79-97cb-48e7-a03b-7f07725b0e87`
3. Property 3: `452b2309-792f-4d94-91fd-6b374075f3f3`

### Payment Methods

1. Payment Method 1: `8cfa68a3-34cf-4da5-aa2f-88c49c31a681` for User 1
2. Payment Method 2: `e1c40b79-97cb-48e7-a03b-7f07725b0e81` for User 2
3. Payment Method 3: `452b2309-792f-4d94-91fd-6b374075f3f1` for User 3

Feel free to use these UUIDs to test the API endpoints.
