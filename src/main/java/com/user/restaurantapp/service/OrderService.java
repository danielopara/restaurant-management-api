package com.user.restaurantapp.service;
import com.user.restaurantapp.dto.OrderDto;


public interface OrderService {
    OrderDto createOrder(OrderDto request);
}
