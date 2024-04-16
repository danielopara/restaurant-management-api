package com.user.restaurantapp.service.impl;


import com.user.restaurantapp.dto.OrderDto;
import com.user.restaurantapp.dto.OrderItemsDto;
import com.user.restaurantapp.model.FoodItem;
import com.user.restaurantapp.model.Order;
import com.user.restaurantapp.model.OrderedItems;
import com.user.restaurantapp.repository.FoodRepository;
import com.user.restaurantapp.repository.OrderRepository;
import com.user.restaurantapp.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class.getName());
    private final FoodRepository foodRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(FoodRepository foodRepository, OrderRepository orderRepository) {
        this.foodRepository = foodRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDto createOrder(OrderDto orderRequestDto) {
        BigDecimal total = BigDecimal.ZERO;
        List<OrderedItems> updatedOrderList = new ArrayList<>();

        for (OrderItemsDto orderItem : orderRequestDto.getOrderList()) {
            Optional<FoodItem> foodItemOptional = foodRepository.findByFoodName(orderItem.getFoodName());
            if (foodItemOptional.isPresent()) {
                FoodItem foodItem = foodItemOptional.get();
                BigDecimal foodPrice = foodItem.getFoodPrice();

                OrderedItems orderedItem = new OrderedItems();
                orderedItem.setFoodName(foodItem.getFoodName());
                int portion = orderItem.getPortion() == 0 ? 1 : orderItem.getPortion();
                orderedItem.setPortion(portion);
                foodPrice = foodPrice.multiply(BigDecimal.valueOf(portion));

                total = total.add(foodPrice);
                updatedOrderList.add(orderedItem);
            } else {
                logger.warn("Food item not found: {}", orderItem.getFoodName());
            }
        }

        orderRequestDto.setTotalPrice(total);

        Order mainOrder = new Order();
        mainOrder.setPersonName(orderRequestDto.getPersonName());
        mainOrder.setOrderList(updatedOrderList);
        mainOrder.setTotalPrice(total);

        try {
            orderRepository.save(mainOrder);
            logger.info("Order created and saved successfully: {}", orderRequestDto.getPersonName());
        } catch (Exception e) {
            logger.error("Failed to save order: {}", e.getMessage(), e);
        }

        return orderRequestDto;
    }


//    @Override
//    public OrderDto createOrder(OrderDto request) {
//        OrderDto orderDto = new OrderDto();
//        Order mainOrder = new Order();
//        BigDecimal total = BigDecimal.ZERO;
//
//        List<OrderedItems> updatedOrderList = new ArrayList<>();
//
//        for(OrderItemsDto order: request.getOrderList()) {
//            Optional<FoodItem> foodItemDto = foodRepository.findByFoodName(order.getFoodName());
//            if(foodItemDto.isPresent()){
//                FoodItem dto = foodItemDto.get();
//
//                BigDecimal foodPrice = dto.getFoodPrice();
//
//
//                OrderedItems orderedItems = new OrderedItems();
//                orderedItems.setFoodName(dto.getFoodName());
//                orderedItems.setPortion(order.getPortion());
//
//
//                total = total.add(foodPrice);
//                updatedOrderList.add(orderedItems);
//            } else {
//
//            }
//        }
//        orderDto.setPersonName(request.getPersonName());
//        orderDto.setOrderList(request.getOrderList());
//        orderDto.setTotalPrice(total);
//
//        mainOrder.setPersonName(request.getPersonName());
//        mainOrder.setOrderList(updatedOrderList);
//        mainOrder.setTotalPrice(total);
//
//        orderRepository.save(mainOrder);
//
//        return orderDto;
//    }
}
