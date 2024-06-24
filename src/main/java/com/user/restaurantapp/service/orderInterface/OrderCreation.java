package com.user.restaurantapp.service.orderInterface;

import com.user.restaurantapp.dto.OrderDto;
import com.user.restaurantapp.response.BaseResponse;

public interface OrderCreation {
    BaseResponse createOrder(OrderDto request);
}
