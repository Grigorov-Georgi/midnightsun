# eCart - Microservice Web Application

## Description:
The E-Commerce Microservices Project is a learning-oriented application that showcases the principles and practices of 
microservices architecture in the context of an e-commerce platform. The project comprises four distinct microservices: 
- OrderService
- ProductService
- NotificationService
- RevRateService (Reviews and Rating Score).

## Microservices Description:

- Order Service: Handles the creation, modification, and cancellation of orders. It interacts with the Product Service 
to fetch product data and to check product availability.

- Product Service: Responsible for managing product information, including name, description, price, quantity etc. It 
handles CRUD operations for products. It communicates with the RevRateService to fetch and display review and rating 
information for each product.

- Notification Service: Sends notifications to customers regarding order creation. It receives events from the Order 
Service and triggers notifications via email.

- RevRate Service: Manages customer reviews and rating scores for products, offering valuable insights and improving 
customer trust.
