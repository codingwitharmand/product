# Product Service (Reactive)

A minimal reactive REST API for managing products, built with Spring Boot 3 (WebFlux) and Reactive MongoDB.

## Features
- Reactive CRUD endpoints under `/api/products`
- Spring WebFlux + Project Reactor
- Reactive MongoDB repository
- HTTP request examples in `requests/products.http`

## Tech Stack
- Java 21
- Spring Boot 3.5 (WebFlux)
- Spring Data MongoDB Reactive
- Maven

## Prerequisites
- Java 21 (verify with: `java -version`)
- Maven (wrapper included: `./mvnw`)
- MongoDB 6.x running locally, or start it via Docker

Quick MongoDB via Docker:

```bash
# Pull and run MongoDB on the default port 27017
docker run --name product-mongo -p 27017:27017 -d mongo:latest
```

The application expects MongoDB at `mongodb://localhost:27017/productdb` (configurable).

## Getting Started

1) Clone and enter the project directory

2) Start MongoDB (see Docker command above, or use a local MongoDB installation)

3) Run the application

```bash
# Using Maven Wrapper
./mvnw spring-boot:run

# Or build a jar and run
./mvnw clean package
java -jar target/product-0.0.1-SNAPSHOT.jar
```

By default the app runs on `http://localhost:8080`.

## Configuration
Configuration lives in `src/main/resources/application.yml`:

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/productdb
```

You can override via environment variables:
- `SPRING_DATA_MONGODB_URI` e.g. `export SPRING_DATA_MONGODB_URI="mongodb://localhost:27017/productdb"`
- `SERVER_PORT` to change the HTTP port (e.g. `export SERVER_PORT=8081`)

## Domain Model
```json
{
  "id": "string (generated)",
  "name": "string",
  "price": 0.0
}
```

## REST API
Base path: `/api/products`

- GET `/api/products` — List all products
- POST `/api/products` — Create a product
- GET `/api/products/{id}` — Get a product by id
- PUT `/api/products/{id}` — Update an existing product (name, price)
- DELETE `/api/products/{id}` — Delete a product by id

### Examples (curl)
List all:
```bash
curl -s http://localhost:8080/api/products
```

Create:
```bash
curl -s -X POST http://localhost:8080/api/products \
  -H 'Content-Type: application/json' \
  -d '{"name":"Laptop","price":1499.99}'
```

Get by id:
```bash
curl -s http://localhost:8080/api/products/<id>
```

Update:
```bash
curl -s -X PUT http://localhost:8080/api/products/<id> \
  -H 'Content-Type: application/json' \
  -d '{"name":"Laptop Pro","price":1899.00}'
```

Delete:
```bash
curl -s -X DELETE http://localhost:8080/api/products/<id>
```

### HTTP file (IntelliJ/IDEA)
You can also use the ready-made HTTP requests in:
```
requests/products.http
```

## Testing
```bash
./mvnw test
```

## Troubleshooting
- MongoDB connection refused
  - Ensure MongoDB is running and listening on `localhost:27017`
  - If using Docker, check the container: `docker ps` and logs: `docker logs product-mongo`
- Java version
  - The project targets Java 21. Ensure your `JAVA_HOME` points to JDK 21.
- Port conflicts
  - Change the server port via `SERVER_PORT`, e.g. `SERVER_PORT=8081 ./mvnw spring-boot:run`

## Project Structure (high level)
```
src/
  main/java/com/cwa/product/
    ProductApplication.java
    controller/ProductController.java
    entity/Product.java
    repository/ProductRepository.java
  main/resources/application.yml
  test/java/com/cwa/product/ProductApplicationTests.java
requests/products.http
pom.xml
```

## License
This project does not declare a license. Add one if you plan to share or distribute the code.
