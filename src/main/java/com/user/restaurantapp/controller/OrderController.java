package com.user.restaurantapp.controller;

import com.user.restaurantapp.dto.OrderDto;
import com.user.restaurantapp.response.BaseResponse;
import com.user.restaurantapp.service.impl.OrderServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/order")
@RestController
@CrossOrigin(origins = "*")
@Tag(name = "Order Service", description = "For managing food orders")
public class OrderController {

    private final OrderServiceImpl orderService;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    private void logRequest( HttpServletRequest request) {
        logger.info("Received a request from: {}", request.getRequestURI());
    }
    private void logError(HttpServletRequest request){
        logger.error("Food not found {} \n Endpoint :{}" , HttpStatus.BAD_REQUEST , request.getRequestURI());
    }

    @GetMapping("/orders")
    @Operation(method = "GET", summary = "Get all orders")
    ResponseEntity<?> getAllOrders(HttpServletRequest request){

        logRequest(request);

        BaseResponse response = orderService.allOrders();
        if(response.getStatus() == HttpStatus.OK.value()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            logger.error("failed to get orders \n Endpoint : {}", request.getRequestURI());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("post-order")
    @Operation(method = "POST", summary = "Order creation", responses = {
            @ApiResponse(responseCode = "200", description = "Order created successfully",
                    content = @Content(schema = @Schema(implementation = OrderDto.class),
                            examples = @ExampleObject(value = "{\n" +
                                    " \"personName\": \"John Doe\",\n" +
                                    " \"orderList\": [\n" +
                                    "    {\n" +
                                    "      \"foodName\": \"Pizza\",\n" +
                                    "      \"portion\": 2\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"foodName\": \"Burger\",\n" +
                                    "      \"portion\": 1\n" +
                                    "    }\n" +
                                    " ],\n" +
                                    " \"totalPrice\": 23.98\n" +
                                    "}"))),
            @ApiResponse(responseCode = "400", description = "Failed to create order")
    })
    ResponseEntity<?> postOrder(@RequestBody OrderDto orderRequest, HttpServletRequest request){

        logRequest(request);
        BaseResponse order = orderService.createOrder(orderRequest);
        if (order.getStatus() == HttpStatus.OK.value()) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            logger.error("failed to post an order \n Endpoint : {}", request.getRequestURI());
            return new ResponseEntity<>("Failed to create order", HttpStatus.BAD_REQUEST);
        }
    }
}
