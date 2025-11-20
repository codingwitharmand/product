# Product Service (Reactive)

A minimal reactive REST API built with Spring Boot 3 (WebFlux) that fetches products from the public DummyJSON API, enriches them with a computed discounted price, and exposes a small analytics endpoint.

## Features
- Spring WebFlux + Project Reactor
- Integrates with DummyJSON products API via `WebClient`
- Endpoint to list products with pagination: `limit` and `skip`
- Enrichment: computes `discountedPrice` when a `discountPercentage` is present
- Analytics: average price per category
- Ready-to-run HTTP request examples in `requests/products.http`

## Tech Stack
- Java 21
- Spring Boot 3.5 (WebFlux)
- Maven

## Prerequisites
- Java 21 (verify with: `java -version`)
- Maven (wrapper included: `./mvnw`)

No database is required. The service calls `https://dummyjson.com` to retrieve data.

## Getting Started

1) Clone and enter the project directory

2) Run the application

```bash
# Using Maven Wrapper
./mvnw spring-boot:run

# Or build a jar and run
./mvnw clean package
java -jar target/product-0.0.1-SNAPSHOT.jar
```

By default the app runs on `http://localhost:8080`.

## Configuration
- Application config: `src/main/resources/application.yml` (currently only the app name)
- Web client config: `src/main/java/com/cwa/product/config/WebClientConfig.java`
  - Base URL: `https://dummyjson.com`
  - Timeout: 10 seconds

If you need to change the base URL or timeouts, update `WebClientConfig` accordingly.

## Domain Models

`ProductResponse` (wrapper):
```json
{
  "products": [],
  "total": 100,
  "skip": 0,
  "limit": 30
}
```

`Product` (fields relevant to the service):
```json
{
  "id": 1,
  "title": "string",
  "description": "string",
  "price": 100.00,
  "category": "string",
  "brand": "string",
  "rating": 4.5,
  "stock": 10,
  "thumbnail": "url",
  "images": ["url", "url"],
  "discountPercentage": 12.5,
  "discountedPrice": 87.50
}
```

Note: `discountedPrice` is computed by this service when `discountPercentage > 0`.

## REST API
Base path: `/api/products`

- GET `/api/products`
  - Query params: `limit` (default `30`), `skip` (default `0`)
  - Response: `ProductResponse`

- GET `/api/products/analytics/price-by-category`
  - Response: JSON object mapping `category -> averagePrice` (rounded to 2 decimals)

### Examples (curl)
List products (defaults):
```bash
curl -s "http://localhost:8080/api/products"
```

List products with pagination:
```bash
curl -s "http://localhost:8080/api/products?limit=20&skip=10"
```

Average price by category:
```bash
curl -s "http://localhost:8080/api/products/analytics/price-by-category"
```

### HTTP file (IntelliJ IDEA)
You can also use the ready-made HTTP requests in:
```
requests/products.http
```

## Testing
```bash
./mvnw test
```

## Troubleshooting
- Network issues reaching DummyJSON
  - Ensure outbound internet access is available from your machine
  - Base URL is `https://dummyjson.com`
- Timeouts
  - Default response timeout is 10s (see `WebClientConfig`)
- Java version
  - The project targets Java 21. Ensure your `JAVA_HOME` points to JDK 21.
- Port conflicts
  - Change the server port via `SERVER_PORT`, e.g. `SERVER_PORT=8081 ./mvnw spring-boot:run`

## Project Structure (high level)
```
src/
  main/java/com/cwa/product/
    ProductApplication.java
    client/DummyJsonClient.java
    config/WebClientConfig.java
    controller/ProductController.java
    exception/ClientException.java
    exception/ServerException.java
    model/Product.java
    model/ProductResponse.java
    service/ProductService.java
  main/resources/application.yml
  test/java/com/cwa/product/ProductApplicationTests.java
requests/products.http
pom.xml
```

## License
This project does not declare a license. Add one if you plan to share or distribute the code.
