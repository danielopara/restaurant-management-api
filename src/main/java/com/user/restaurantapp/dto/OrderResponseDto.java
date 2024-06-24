package com.user.restaurantapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto{
    private String personName;
    private List<OrderItemsDto> orderList;
    private BigDecimal totalPrice;
}