package com.user.restaurantapp.service;
import com.user.restaurantapp.dto.OrderDto;
import com.user.restaurantapp.response.BaseResponse;
import com.user.restaurantapp.service.orderInterface.OrderCreation;
import com.user.restaurantapp.service.orderInterface.OrderRetrieval;


public interface OrderService extends OrderRetrieval, OrderCreation {

}
