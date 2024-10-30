# Shopping Cart Backend Project

Master Spring Boot and Spring Security by building a shopping cart backend project. This project covers essential concepts, including entity mapping, CRUD operations, service and controller development, and integrating Spring Security with JWT for authentication.

## Table of Contents

- [Introduction](#introduction)
- [Project Setup](#project-setup)
- [Key Features](#key-features)
- [Technologies Used](#technologies-used)
- [Endpoints](#endpoints)
- [Installation](#installation)
- [Testing](#testing)
- [Contributions](#contributions)

## Introduction

This project is designed to provide a comprehensive understanding of building a backend application using Spring Boot and Spring Security. Throughout the project, you will learn how to create and manage various components such as products, categories, and shopping carts.

## Project Setup

### Contents
1. **Generating the project** - Initial setup and project structure.
2. **Creating and mapping entity classes** - Define entities for products, categories, carts, and orders.
3. **Implementing CRUD operations** - Develop CRUD functionalities for products and categories.
4. **Service and Controller Development** - Create services and controllers for managing product images, categories, and carts.
5. **Testing the APIs** - Verify functionality through comprehensive API testing.
6. **Integrating Spring Security** - Secure the application and manage user authentication.

## Key Features

- Product Management (CRUD operations)
- Category Management
- Shopping Cart Functionality
- User Authentication and Authorization using Spring Security and JWT
- Order Management

## Technologies Used

- **Java**
- **Spring Boot**
- **Spring Security**
- **JWT (JSON Web Tokens)**
- **MySQL (or any other database you choose)**
- **Maven** for dependency management
- **Postman** for testing APIs

## Endpoints

- **Products**
  - `GET /api/products`
  - `POST /api/products`
  - `PUT /api/products/{id}`
  - `DELETE /api/products/{id}`
  
- **Categories**
  - `GET /api/categories`
  - `POST /api/categories`
  
- **Cart**
  - `POST /api/cart/add`
  - `DELETE /api/cart/remove`
  - `PUT /api/cart/update`

- **Orders**
  - `GET /api/orders`
  - `POST /api/orders`

- **User Authentication**
  - `POST /api/auth/login`

## Installation

1. Clone the repository:
   **git clone https://github.com/Mirgubad/Java-ECommerce**

2. Navigate to the project directory:
   **cd shopping-cart-backend**

3. Install dependencies:
   **mvn install**
   
5. Set up your database and configure application properties.

6. Run the application:
   **mvn spring-boot:run**


**Testing**
Use Postman or any other API testing tool to test the endpoints listed above. Ensure to test authentication endpoints to validate JWT functionality.

Contributions
Contributions are welcome! Please create a pull request or submit issues for any bugs or improvements.
