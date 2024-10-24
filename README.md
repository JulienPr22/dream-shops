# DreamShops

**DreamShops** is an e-commerce application designed to provide a seamless shopping experience for users. Built with a robust Java backend using Spring Boot and JPA, and a user-friendly frontend, this application allows users to browse products, manage their shopping carts, and place orders effortlessly.

## Features

- **User Management**: Users can create accounts, log in, and manage their profiles using email and password authentication, secured with Spring Security.
- **JWT Authentication**: The API is protected by JWT tokens, ensuring secure access to user-specific resources.
- **Role-Based Access Control**: Different roles can be assigned to users, providing varying levels of access to the application.
- **Product Catalog**: A comprehensive catalog of products, complete with details such as name, brand, description, and price.
- **Shopping Cart**: Users can add products to their cart, adjust quantities, and view the total amount before checkout.
- **Order Management**: Users can place orders for the items in their cart, with a history of their past orders accessible for review.
- **Category Management**: Products are organized into categories, making it easy for users to find what they are looking for.
- **Dynamic Pricing**: The application calculates the total price based on the items in the cart and their quantities.

## Tech Stack

- **Backend**: Java, Spring Boot, JPA, Hibernate, Spring Security
- **Authentication**: JWT (JSON Web Tokens) for secure API access
- **Database**: PostgreSQL
- **Frontend**: [Your chosen frontend framework, e.g., React, Angular, etc.]
- **Model Mapping**: ModelMapper for object conversion between DTOs and entities.

## Installation

To run this application locally:

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/dreamshops.git
   cd dreamshops
2. Add an application.properties file with your configuration
   ```bash
    spring.application.name=dream-shops
    
    server.port=9193
    
    spring.datasource.url=jdbc:mysql://localhost:3306/dream_shops_db
    spring.datasource.username=root
    spring.datasource.password=
    
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
    
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
    spring.jpa.hibernate.ddl-auto=update
    
    spring.servlet.multipart.max-file-size=5MB
    spring.servlet.multipart.max-request-size=5MB
    
    api.prefix=/api/v1
    
    auth.token.expirationInMils=3600000
    auth.token.jwtSecret=8cbb0c172a74a3884bbd62a327b005677da8a8a5c2b91aaad70e9e3dbe5dcbe2

3. Run the project
