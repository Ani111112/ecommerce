# Ecommerce REST API

## Project Overview
This project is a backend REST API for an Ecommerce platform. It allows users to manage products, categories, orders, and authentication. The API is built using **Spring Boot** with **Maven** for dependency management and supports JWT-based authentication.  

It provides a scalable foundation for ecommerce operations and is ready for integration with frontend applications.

---

## Features Implemented

### Items
- CRUD operations for products: Create, Read, Update, Delete
- Categories support multiple products
- Hierarchical structure for categories (parent-child)
- Search and filter products by name, category, and price  

### Orders
- Create orders from a user's cart
- Retrieve, update, and cancel orders
- View order history per user  

### User Authentication
- Sign-up and sign-in with secure password storage
- JWT-based authentication for protected endpoints  

### Miscellaneous
- Input validation using annotations (`@NotNull`, `@Size`, etc.)
- Centralized error handling for consistent API responses
- Swagger/OpenAPI documentation for API exploration
- Unit and integration tests included in `src/test/java`

---

## API Endpoints

### Items
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/api/items` | Retrieve all items |
| GET | `/api/items/{itemId}` | Retrieve a specific item by ID |
| POST | `/api/items` | Create a new item |
| PUT | `/api/items/{itemId}` | Update an existing item |
| DELETE | `/api/items/{itemId}` | Delete an item |

### Orders
| Method | Endpoint | Description |
|--------|---------|-------------|
| POST | `/api/orders` | Create a new order |
| GET | `/api/orders` | Retrieve all orders |
| GET | `/api/orders/{orderId}` | Retrieve a specific order by ID |
| PUT | `/api/orders/{orderId}` | Update an existing order |
| DELETE | `/api/orders/{orderId}` | Cancel an order |

### Users
| Method | Endpoint | Description |
|--------|---------|-------------|
| POST | `/api/users/register` | Register a new user |
| POST | `/api/users/login` | Login and obtain JWT token |

---

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.9+
- H2 database (in-memory)  

### Installation
1. Clone the repository:  
```bash
git clone https://github.com/Ani111112/ecommerce.git
cd ecommerce
```

### Build and Run
Build the project using Maven:

```bash
mvn clean install

```
### Configuration

The application uses an **in-memory H2 database**. Configure it in `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:ecommerce_db
    username: sa
    password: 
  jpa:
    hibernate:
      ddl-auto: update
  h2:
    console:
      enabled: true
      path: /h2-console

```
### 3️⃣ **H2 Console Note**
Add a small note so the H2 console is easy to access:

```markdown
- **H2 Console:** Access at [http://localhost:8080/h2-console](http://localhost:8080/h2-console)  
  Use JDBC URL `jdbc:h2:mem:ecommerce_db` and username & password `test` to login.
```

### API Documentation
Swagger UI is available at:  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Features Summary
- CRUD operations for items and categories
- Order creation, management, and history
- User authentication with JWT
- Input validation and error handling
- Swagger/OpenAPI documentation
- H2 in-memory database for testing
- Unit and integration testing included

### Technologies Used
- Java 17
- Spring Boot
- Maven
- H2 database
- JWT authentication
- Swagger/OpenAPI
- JUnit & Mockito

### Project Structure
```bash
src/
├─ main/java/com/ecommerce/
│ ├─ controller/ # REST endpoints (ItemController, OrderController, UserController)
│ ├─ service/ # Business logic
│ ├─ repository/ # Database access
│ ├─ model/ # Entity classes (Item, Order, User, Category)
│ └─ security/ # JWT and authentication filters
└─ main/resources/
├─ application.yml # Database & security configuration
└─ test/java/com/ecommerce/ # Unit & integration tests
