## Table of Contents
- [Introduction](#introduction)
  - [Overview](#overview)
  - [Features](#features)
  - [Technologies Used](#technologies-used)
- [Architecture Overview](#architecture-overview)
  - [System Design Overview](#system-design-overview)
  - [Microservices Responsibilities](#microservices-responsibilities)
    - [User Service](#1-user-service)
    - [Product Service](#2-product-service)
    - [Cart Service](#3-cart-service)
    - [API Gateway](#4-api-gateway)
    - [Config Server](#5-config-server)
    - [Eureka Discovery Server](#6-eureka-discovery-server)
- [Entity-Relationship Diagram (ERD)](#entity-relationship-diagram-erd)
- [API Documentation](#api-documentation)
  - [User Service](#user-service)
  - [Product Service](#product-service)
  - [Cart Service](#cart-service)

# Introduction

## Overview
This project is a backend system for an e-commerce platform, built using a **microservices architecture**. Each part of the system is handled by a separate service, making it easier to scale and maintain.

The backend includes services for **user management, product catalog, and shopping cart operations**. It also has an **API Gateway** to direct requests and a **configuration server** to manage settings across services.

## Features
- **Separate services** for users, products, and cart operations
- **RESTful APIs** for communication between services
- **Each service has its own MySQL database** for better organization
- **Docker** is used to run the databases in isolated environments
- **A central configuration server** manages settings for all services
- **Eureka Discovery Server** helps services find each other
- **An API Gateway** directs external requests to the right service

## Technologies Used
- **Spring Boot** – Backend framework for each service
- **Spring Cloud Gateway** – Manages incoming API requests
- **Eureka Discovery Server** – Helps services register and find each other
- **Spring Cloud Config** – Stores shared configuration settings
- **MySQL** – Stores user, product, and cart data
- **Docker** – Runs each database in a separate container
- **Postman** – Used for testing API requests

# Architecture Overview

## System Design Overview
This project follows a **microservices architecture**, meaning each core feature of the system is handled by a separate service. This approach makes the backend more **scalable, modular, and easier to maintain**. Each service runs independently and communicates with others through REST APIs.

## Microservices Responsibilities
The backend consists of the following services:

### 1. User Service
- Manages user accounts, and user details
- Stores users data in a MySQL database

### 2. Product Service
- Manages the product catalog, including adding, updating, and retrieving product details
- Stores product data in a MySQL database

### 3. Cart Service
- Handles shopping cart operations like adding/removing products
- Stores cart data specific to each user

### 4. API Gateway
- Routes incoming client requests to the appropriate microservice
- Acts as a single entry point for external users

### 5. Config Server
- Centralized service for managing configuration properties of all microservices
- Ensures consistency across environments

### 6. Eureka Discovery Server
- Allows microservices to register themselves and discover other services
- Helps in dynamic scaling and load balancing

# Entity-Relationship Diagram (ERD)

The backend uses separate MySQL databases for each microservice. Each service has its own tables based on its responsibilities. Here’s a visual representation of the relationships between the entities:

![ERD](https://github.com/user-attachments/assets/19f5587f-de00-48b2-b4f8-3e0b935d6cee)

# API Documentation

Each microservice exposes a set of RESTful API endpoints. Below is a summary of the main API routes for each service.

All services return standard HTTP status codes (200, 201, 400, 401, 404, 500) with JSON responses.

**Base URL for all endpoints is:** `/api/v1`

## User Service

### Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/users` | Get all users |
| GET    | `/users/{userId}` | Get specific user using ID |
| GET    | `/users/{userId}/details` | Get specific user details by ID |
| POST   | `/users` | Create new user |
| PUT    | `/users/{userId}` | Update user information |
| DELETE | `/users/{userId}` | Delete user |

#### Example: Register a User
**POST** `/api/v1/users`

**Request**
```json
{
	"email": "andrew@gmail.com",
	"password": "securepassword",
	"firstName": "andrew",
	"lastName": "ezzat",
	"address": "egypt",
	"phoneNumber": "01234567891"
}
```

**Response**
```json
{
	"message": "User Created Successfully!",
	"status": "201",
	"timeStamp": "2025-04-04T12:34:56Z",
	"data": [
		"userId": 5,
		"email": "andrew@gmail.com",
		"createdAt": "2025-04-04T22:31:39.261512"
	]
}
```

If the email is already registered:

**Response**
```json
{
	"message": "Already have an account with this email!",
	"status": 400,
	"timeStamp": "2025-04-04T22:32:09.489168",
	"data": null
}
```

## Product Service

### Endpoints
|Method|Endpoint|Description|
|---|---|---|
|GET|`/products`|Get all products|
|GET|`/products/{productId}`|Get product by ID|
|GET|`/products/{productId}/categories`|Get categories of a product|
|POST|`/products`|Add a new product|
|PUT|`/products/{productId}`|Update product information|
|DELETE|`/products/{productId}`|Delete a product|

|Method|Endpoint|Description|
|---|---|---|
|GET|`/categories`|Get all categories|
|GET|`/categories/{categoryId}`|Get category by ID|
|GET|`/categories/{categoryId}/products`|Get products under a category|
|POST|`/categories`|Create a new category|
|PUT|`/categories/{categoryId}`|Update a category|
|DELETE|`/categories/{categoryId}`|Delete a category|

#### Example: Get Products in a Category
**GET** `/api/v1/categories/1/products`

**Response**
```json
{
    "message": "All Products of Category id: 1",
    "status": 200,
    "timestamp": "2025-04-04T22:34:31.357805",
    "data": [
        {
            "productId": 9,
            "name": "PS5",
            "price": 1540.42,
            "image": "https://m.media-amazon.com/images/I/41fQboS8JBL.jpg",
            "availableQuantity": 3,
            "createdAt": "2025-03-28T03:11:25.460635"
        }
    ]
}
```

If the category ID does not exist:

**Response**
```json
{
    "message": "Category not found with id: 3",
    "status": 404,
    "timestamp": "2025-04-04T22:36:37.025182",
    "data": null
}
```

## Cart Service

### Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/carts` | Get all carts |
| GET    | `/carts/{userId}` | Get a specific user's cart |
| GET    | `/carts/{cartId}/items` | Get items in a cart |
| POST   | `/carts/{cartId}/items` | Add item to a cart |
| DELETE | `/carts/{cartId}/items/{productId}` | Remove item from a cart |

#### Example: Add Item to Cart
**POST** `/api/v1/carts/1/items`

**Request**
```json
{
  "productId": 9,
  "quantity": 2
}
```

**Response**
```json
{
    "message": "Cart item created successfully!",
    "status": 201,
    "timeStamp": "2025-04-04T22:40:17.678162",
    "data": {
        "id": {
            "cartId": 1,
            "productId": 9
        },
        "cart": {
            "cartId": 1,
            "userId": 8
        },
        "productId": 9,
        "quantity": 2
    }
}
```

