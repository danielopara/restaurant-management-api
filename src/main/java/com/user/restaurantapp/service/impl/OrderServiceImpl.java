package com.user.restaurantapp.service.impl;


import com.user.restaurantapp.dto.OrderDto;
import com.user.restaurantapp.dto.OrderResponseDto;
import com.user.restaurantapp.model.Order;
import com.user.restaurantapp.repository.OrderRepository;
import com.user.restaurantapp.response.BaseResponse;
import com.user.restaurantapp.service.OrderService;
import com.user.restaurantapp.service.orderInterface.OrderCalculator;
import com.user.restaurantapp.service.orderInterface.OrderValidator;
import com.user.restaurantapp.validation.OrderValidation;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class.getName());
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final OrderCalculator orderCalculator;

    public OrderServiceImpl(OrderRepository orderRepository, OrderValidator orderValidator, OrderCalculator orderCalculator) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.orderCalculator = orderCalculator;
    }

    @Override
    public BaseResponse createOrder(OrderDto orderRequestDto) {
        try {
            OrderValidation orderValidation = orderValidator.validateOrderItems(orderRequestDto.getOrderList());
            if(!orderValidation.isValid()){
                return new BaseResponse(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "one or more items not found",
                        null,
                        null
                );
            }

            Order mainOrder = orderCalculator.calculateOrder(orderRequestDto);
            orderRepository.save(mainOrder);

            OrderResponseDto responseDto = new OrderResponseDto();
            responseDto.setPersonName(orderRequestDto.getPersonName());
            responseDto.setTotalPrice(mainOrder.getTotalPrice());
            responseDto.setOrderList(orderRequestDto.getOrderList());

            logger.info("Order created and saved successfully: {}", orderRequestDto.getPersonName());
            return new BaseResponse(
                    HttpServletResponse.SC_OK,
                    "Order created",
                    responseDto,
                    null
            );


//            BigDecimal total = BigDecimal.ZERO;
//            List<OrderedItems> updatedOrderList = new ArrayList<>();
//
//            for (OrderItemsDto orderItem : orderRequestDto.getOrderList()) {
//
//                Optional<FoodItem> foodItemOptional = foodRepository.findByFoodName(orderItem.getFoodName());
//                if (foodItemOptional.isEmpty()) {
//                    logger.warn("Food item not found: {}", orderItem.getFoodName());
//                    return new BaseResponse(
//                            HttpServletResponse.SC_OK,
//                            "Item not found",
//                            orderItem.getFoodName(),
//                            null
//                    );
//                }
//            }
//
//            for (OrderItemsDto orderItem : orderRequestDto.getOrderList()) {
//                Optional<FoodItem> foodItemOptional = foodRepository.findByFoodName(orderItem.getFoodName());
//                if(foodItemOptional.isPresent()){
//                    FoodItem foodItem = foodItemOptional.get();
//                    BigDecimal foodPrice = foodItem.getFoodPrice();
//
//                    OrderedItems orderedItem = new OrderedItems();
//                    orderedItem.setFoodName(foodItem.getFoodName());
//                    int portion = orderItem.getPortion() == 0 ? 1 : orderItem.getPortion();
//                    orderedItem.setPortion(portion);
//                    foodPrice = foodPrice.multiply(BigDecimal.valueOf(portion));
//
//                    total = total.add(foodPrice);
//                    updatedOrderList.add(orderedItem);
//                }
//            }
//
//            Order mainOrder = new Order();
//            String refNumber = mainOrder.generateRandomString();
//            mainOrder.setPersonName(orderRequestDto.getPersonName());
//            mainOrder.setRefNo(refNumber);
//            mainOrder.setOrderList(updatedOrderList);
//            mainOrder.setTotalPrice(total);
//            orderRepository.save(mainOrder);
//
//            OrderResponseDto responseDto = new OrderResponseDto();
//            responseDto.setPersonName(orderRequestDto.getPersonName());
//            responseDto.setOrderList(orderRequestDto.getOrderList());
//            responseDto.setTotalPrice(total);
//
//            logger.info("Order created and saved successfully: {}", orderRequestDto.getPersonName());
//            return new BaseResponse(
//                    HttpServletResponse.SC_OK,
//                    "Order created",
//                    responseDto,
//                    null
//            );
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
    @Transactional(readOnly = true)
    public BaseResponse allOrders() {
        try {
            List<Order> orders = orderRepository.findAll();
            for (Order order : orders) {
                Hibernate.initialize(order.getOrderList());
            }

            logger.info("retrieved all orders");
            return new BaseResponse(
                    HttpServletResponse.SC_OK,
                    "Orders",
                    orders,
                    null
            );
        } catch (Exception e) {
            logger.error("failed to get all orders");
            return new BaseResponse(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "ERROR",
                    null,
                    e.getMessage()
            );
        }
    }
}

