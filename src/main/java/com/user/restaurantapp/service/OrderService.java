package com.user.restaurantapp.service;
import com.user.restaurantapp.dto.OrderDto;
import com.user.restaurantapp.response.BaseResponse;


public interface OrderService {
    BaseResponse createOrder(OrderDto request);
    BaseResponse allOrders();
}
