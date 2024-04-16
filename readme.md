# Restaurant App API Documentation

## Purpose

The Restaurant App API is designed to manage food items and orders within a restaurant application. It provides a RESTful interface for adding, retrieving, and managing food items, as well as creating orders.

## Requirements

- Java 8 or higher
- Spring Boot 3.x
- Maven
- A relational database (MySQL)

## Schemas

### FoodItem

Represents a food item in the system.

- `id`: Long (read-only) - The unique identifier of the food item.
- `foodName`: String (required) - The name of the food item.
- `foodPrice`: BigDecimal (required) - The price of the food item.

### Order

Represents an order in the system.

- `order_id`: Long (read-only) - The unique identifier of the order.
- `personName`: String (required) - The name of the person placing the order.
- `orderList`: List<OrderedItems> (required) - A list of items in the order.
- `totalPrice`: BigDecimal (required) - The total price of the order.

### OrderedItems

Represents an item in an order.

- `id`: Long (read-only) - The unique identifier of the ordered item.
- `foodName`: String (required) - The name of the food item.
- `portion`: Integer (required) - The number of portions of the food item.
- `order`: Order (read-only) - The order this item belongs to.

## DTOs

### FoodItemDto

Represents a food item in the system.

- `foodName`: String (required) - The name of the food item.
- `foodPrice`: BigDecimal (required) - The price of the food item.

### AddFoodDto

Represents the response after adding a new food item.

- `message`: String - A message indicating the result of the operation.
- `item`: Object - Details about the added food item.

### OrderDto

Represents an order in the system.

- `personName`: String (required) - The name of the person placing the order.
- `orderList`: List<OrderItemsDto> (required) - A list of items in the order.
- `totalPrice`: BigDecimal (required) - The total price of the order.

### OrderItemsDto

Represents an item in an order.

- `foodName`: String (required) - The name of the food item.
- `portion`: Integer (required) - The number of portions of the food item.

## Endpoints

### Food Management

#### POST /api/v1/food/add-food

Adds a new food item to the system.

- **Request Body**: `FoodItemDto`
- **Response**: `200 OK` with `AddFoodDto` if successful.

#### GET /api/v1/food

Retrieves a list of all food items.

- **Response**: `200 OK` with a list of `FoodItemDto` objects.

#### GET /api/v1/food/{foodName}

Retrieves a food item by name.

- **Path Parameter**: `foodName` - The name of the food item.
- **Response**: `200 OK` with a `FoodItemDto` object if found.

### Order Management

#### POST /api/v1/order

Creates a new order.

- **Request Body**: `OrderDto`
- **Response**: `200 OK` with `OrderDto` if successful.

[//]: # (#### GET /api/v1/order/{orderId})

[//]: # ()
[//]: # (Retrieves an order by its ID.)

[//]: # ()
[//]: # (- **Path Parameter**: `orderId` - The ID of the order.)

[//]: # (- **Response**: `200 OK` with an `OrderDto` object if found.)

### Example Requests and Responses

#### Example Request to Add a Food Item

```json
POST /api/v1/food/add-food
Content-Type: application/json

{
 "foodName": "Pizza",
 "foodPrice": 10.99
}
```

#### Example Response

```json
HTTP/1.1 200 OK
Content-Type: application/json

{
 "message": "Food added",
 "item": {
    "foodName": "Pizza",
    "foodPrice": 10.99
 }
}
```

## Getting Started

1. Clone the repository.
2. Configure your database connection in `application.properties`.
3. Run the application using your IDE or Maven.
4. Access the Swagger UI at `http://localhost:8080/swagger-ui.html` to interact with the API.

## Contributing

Contributions are welcome! Please read the contributing guidelines before submitting a pull request.
