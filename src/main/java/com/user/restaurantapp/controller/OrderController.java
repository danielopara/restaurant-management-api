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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/order")
@RestController
@Tag(name = "Order Service", description = "For managing food orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
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
    ResponseEntity<?> postOrder(@RequestBody OrderDto request){
        BaseResponse order = orderService.createOrder(request);
        if (order.getStatus() == HttpStatus.OK.value()) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to create order", HttpStatus.BAD_REQUEST);
        }
    }
}
