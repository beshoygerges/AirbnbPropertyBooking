
```markdown
# Property Booking System

This guide will walk you through the steps to set up and run the Property Booking System application.

## Prerequisites

- JDK (Java Development Kit) 17
- Maven
- Git
```

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

## Accessing the API

### 1- Postman Collection

You can import the provided Postman collection file to test the APIs:

#### Host & Stay.postman_collection.json

### 2- Swagger

Alternatively, you can access the API documentation using Swagger UI:

- Open a web browser
- Navigate to `http://localhost:8080/swagger-ui.html`

You can use either Postman or Swagger to interact with the API endpoints, make requests, and test the functionality of the application.

To use the API endpoints, you'll need UUIDs for users, properties, and payment methods. Here are some example UUIDs:

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
