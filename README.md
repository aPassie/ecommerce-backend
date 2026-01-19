# E-Commerce Backend API

A simple e-commerce backend built with Spring Boot.

## Tech Stack

- Java 25
- Spring Boot 4.0.1
- Spring Data JPA
- Maven

## Setup Instructions

### Prerequisites

- Java 25 or higher
- Maven 3.x

### Running the Application

1. Clone the repository
```bash
git clone <repository-url>
cd E-Commerce-Backend
```

2. Build the project
```bash
./mvnw clean install
```

3. Run the application
```bash
./mvnw spring-boot:run
```

The server will start at `http://localhost:8080`

## API Endpoints

### Products
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/products | Get all products |
| GET | /api/products/{id} | Get product by ID |
| POST | /api/products | Create product |
| PUT | /api/products/{id} | Update product |
| DELETE | /api/products/{id} | Delete product |

### Cart
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/cart/{userId} | Get cart items |
| POST | /api/cart/{userId} | Add item to cart |
| PUT | /api/cart/{userId}/{itemId} | Update cart item |
| DELETE | /api/cart/{userId}/{itemId} | Remove item |
| DELETE | /api/cart/{userId} | Clear cart |

### Orders
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/orders/user/{userId} | Get user orders |
| GET | /api/orders/{orderId} | Get order by ID |
| GET | /api/orders/{orderId}/items | Get order items |
| POST | /api/orders/{userId} | Create order from cart |
| PUT | /api/orders/{orderId}/status | Update order status |

### Payments
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/payments/{orderId} | Create payment |
| GET | /api/payments/{id} | Get payment |
| GET | /api/payments/order/{orderId} | Get payment by order |
| POST | /api/payments/{id}/pay | Process payment |

### Webhooks
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/webhooks/payment | Payment webhook callback |

## Testing with Postman

Import `E-Commerce-API.postman_collection.json` into Postman.

### Test Flow

1. Create a product
2. Add product to cart
3. Create order from cart
4. Create payment for order
5. Process payment
6. Verify order status is PAID

## Project Structure

```
src/main/java/com/shanks/E_Commerce/Backend/
├── controller/
├── service/
├── repository/
├── model/
├── dto/
├── webhook/
├── client/
└── config/
```

## Database Schema

### Entities

**USER**
| Field | Type | Constraint |
|-------|------|------------|
| id | String | PK |
| username | String | |
| email | String | |
| role | String | Optional |

**PRODUCT**
| Field | Type | Constraint |
|-------|------|------------|
| id | String | PK |
| name | String | |
| description | String | Optional |
| price | Double | |
| stock | Integer | |

**CART_ITEM**
| Field | Type | Constraint |
|-------|------|------------|
| id | String | PK |
| userId | String | FK -> USER |
| productId | String | FK -> PRODUCT |
| quantity | Integer | |

**ORDER**
| Field | Type | Constraint |
|-------|------|------------|
| id | String | PK |
| userId | String | FK -> USER |
| totalAmount | Double | |
| status | String | CREATED, PAID, FAILED, CANCELLED |
| createdAt | LocalDateTime | |

**ORDER_ITEM**
| Field | Type | Constraint |
|-------|------|------------|
| id | String | PK |
| orderId | String | FK -> ORDER |
| productId | String | FK -> PRODUCT |
| quantity | Integer | |
| price | Double | |

**PAYMENT**
| Field | Type | Constraint |
|-------|------|------------|
| id | String | PK |
| orderId | String | FK -> ORDER |
| amount | Double | |
| status | String | PENDING, SUCCESS, FAILED |
| paymentId | String | External payment ID |
| createdAt | LocalDateTime | |

### ER Diagram

```
+--------+       +------------+       +---------+
|  USER  |       | CART_ITEM  |       | PRODUCT |
+--------+       +------------+       +---------+
| id (PK)|<------| userId(FK) |       | id (PK) |
| username       | productId  |------>| name    |
| email  |       | quantity   |       | desc    |
| role   |       +------------+       | price   |
+--------+                            | stock   |
    |                                 +---------+
    |                                      |
    v                                      v
+--------+       +------------+            |
| ORDER  |       | ORDER_ITEM |            |
+--------+       +------------+            |
| id (PK)|<------| orderId(FK)|            |
| oderId |       | productId  |<-----------+
| total  |       | quantity   |
| status |       | price      |
| created|       +------------+
+--------+
    |
    | (1:1)
    v
+---------+
| PAYMENT |
+---------+
| id (PK) |
| oderId  |
| amount  |
| status  |
| paymentId
| created |
+---------+
```

### Relationships

```
USER (1) -----> (N) CART_ITEM
USER (1) -----> (N) ORDER
PRODUCT (1) -----> (N) CART_ITEM
PRODUCT (1) -----> (N) ORDER_ITEM
ORDER (1) -----> (N) ORDER_ITEM
ORDER (1) -----> (1) PAYMENT
```
