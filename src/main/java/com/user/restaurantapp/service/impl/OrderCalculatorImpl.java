package com.user.restaurantapp.service.impl;

import com.user.restaurantapp.dto.OrderDto;
import com.user.restaurantapp.dto.OrderItemsDto;
import com.user.restaurantapp.model.FoodItem;
import com.user.restaurantapp.model.Order;
import com.user.restaurantapp.model.OrderedItems;
import com.user.restaurantapp.repository.FoodRepository;
import com.user.restaurantapp.service.orderInterface.OrderCalculator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderCalculatorImpl implements OrderCalculator {
    private final FoodRepository foodRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderCalculatorImpl.class.getName());

    public OrderCalculatorImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public Order calculateOrder(OrderDto orderRequestDto) {
        BigDecimal total = BigDecimal.ZERO;
        List<OrderedItems> updatedOrderList = new ArrayList<>();

        for(OrderItemsDto orderItemsDto : orderRequestDto.getOrderList()){
            FoodItem foodItem = foodRepository.findByFoodName(orderItemsDto.getFoodName()).get();
            BigDecimal foodPrice = foodItem.getFoodPrice();
            OrderedItems orderedItems = new OrderedItems();
            orderedItems.setFoodName(foodItem.getFoodName());
            int portion = orderItemsDto.getPortion() == 0 ? 1 : orderItemsDto.getPortion();
            orderedItems.setPortion(portion);
            foodPrice = foodPrice.multiply(BigDecimal.valueOf(portion));
            total = total.add(foodPrice);
            updatedOrderList.add(orderedItems);
        }
        Order mainOrder = new Order();
        mainOrder.setPersonName(orderRequestDto.getPersonName());
        mainOrder.setRefNo(mainOrder.generateRandomString());
        mainOrder.setOrderList(updatedOrderList);
        mainOrder.setTotalPrice(total);

        return mainOrder;
    }
}
