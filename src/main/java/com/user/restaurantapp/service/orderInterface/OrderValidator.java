package com.user.restaurantapp.service.orderInterface;

import com.user.restaurantapp.dto.OrderItemsDto;
import com.user.restaurantapp.validation.OrderValidation;

import java.util.List;

public interface OrderValidator {
    OrderValidation validateOrderItems(List<OrderItemsDto> orderItems);
}
