package com.user.restaurantapp.service.orderInterface;

import com.user.restaurantapp.dto.OrderDto;
import com.user.restaurantapp.model.Order;

public interface OrderCalculator {
    Order calculateOrder(OrderDto orderRequestDto);
}
