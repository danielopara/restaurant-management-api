package com.user.restaurantapp.service.impl;

import com.user.restaurantapp.dto.OrderItemsDto;
import com.user.restaurantapp.repository.FoodRepository;
import com.user.restaurantapp.service.orderInterface.OrderValidator;
import com.user.restaurantapp.validation.OrderValidation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderValidatorImpl implements OrderValidator {
    private final FoodRepository foodRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderValidatorImpl.class.getName());

    public OrderValidatorImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public OrderValidation validateOrderItems(List<OrderItemsDto> orderItems) {
        List<String> missingItems = new ArrayList<>();
        for (OrderItemsDto orderItem : orderItems) {
            if (foodRepository.findByFoodName(orderItem.getFoodName()).isEmpty()) {
                missingItems.add(orderItem.getFoodName());
                logger.warn("Food item not found: {}", orderItem.getFoodName());
            }
        }
        return new OrderValidation(missingItems.isEmpty(), missingItems);
    }
}
