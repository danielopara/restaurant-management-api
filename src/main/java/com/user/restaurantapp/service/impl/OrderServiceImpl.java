package com.user.restaurantapp.service.impl;


import com.user.restaurantapp.dto.OrderDto;
import com.user.restaurantapp.dto.OrderItemsDto;
import com.user.restaurantapp.model.FoodItem;
import com.user.restaurantapp.model.Order;
import com.user.restaurantapp.model.OrderedItems;
import com.user.restaurantapp.repository.FoodRepository;
import com.user.restaurantapp.repository.OrderRepository;
import com.user.restaurantapp.response.BaseResponse;
import com.user.restaurantapp.service.OrderService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    public BaseResponse createOrder(OrderDto orderRequestDto) {
        try {
            BigDecimal total = BigDecimal.ZERO;
            List<OrderedItems> updatedOrderList = new ArrayList<>();

            for (OrderItemsDto orderItem : orderRequestDto.getOrderList()) {
                Optional<FoodItem> foodItemOptional = foodRepository.findByFoodName(orderItem.getFoodName());
                if (!foodItemOptional.isPresent()) {
                    logger.warn("Food item not found: {}", orderItem.getFoodName());
                    return new BaseResponse(
                            HttpServletResponse.SC_OK,
                            "Item not found",
                            orderItem.getFoodName(),
                            null
                    );
                }
            }

            for (OrderItemsDto orderItem : orderRequestDto.getOrderList()) {
                Optional<FoodItem> foodItemOptional = foodRepository.findByFoodName(orderItem.getFoodName());
                if(foodItemOptional.isPresent()){
                    FoodItem foodItem = foodItemOptional.get();
                    BigDecimal foodPrice = foodItem.getFoodPrice();

                    OrderedItems orderedItem = new OrderedItems();
                    orderedItem.setFoodName(foodItem.getFoodName());
                    int portion = orderItem.getPortion() == 0 ? 1 : orderItem.getPortion();
                    orderedItem.setPortion(portion);
                    foodPrice = foodPrice.multiply(BigDecimal.valueOf(portion));

                    total = total.add(foodPrice);
                    updatedOrderList.add(orderedItem);
                }
            }

            Order mainOrder = new Order();
            String refNumber = mainOrder.generateRandomString();
            mainOrder.setPersonName(orderRequestDto.getPersonName());
            mainOrder.setRefNo(refNumber);
            mainOrder.setOrderList(updatedOrderList);
            mainOrder.setTotalPrice(total);
            orderRepository.save(mainOrder);

            OrderResponseDto responseDto = new OrderResponseDto();
            responseDto.setPersonName(orderRequestDto.getPersonName());
            responseDto.setOrderList(orderRequestDto.getOrderList());
            responseDto.setTotalPrice(total);

            logger.info("Order created and saved successfully: {}", orderRequestDto.getPersonName());
            return new BaseResponse(
                    HttpServletResponse.SC_OK,
                    "Order created",
                    responseDto,
                    null
            );
        } catch (Exception e) {
            logger.error("Failed to save order: {}", e.getMessage(), e);
            return new BaseResponse(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Error",
                    null,
                    e.getMessage()
            );
        }

    }

    @Override
    public BaseResponse allOrders() {
//        try{
            List<Order> orders = orderRepository.findAll();
            return new BaseResponse(
                    HttpServletResponse.SC_OK,
                    "Orders",
                    orders,
                    null
            );
//        } catch (Exception e){
//            return new BaseResponse(
//                    HttpServletResponse.SC_BAD_REQUEST,
//                    "ERROR",
//                    null,
//                    null
//            );
//        }
    }
}
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class OrderResponseDto{
    private String personName;
    private List<OrderItemsDto> orderList;
    private BigDecimal totalPrice;
}
