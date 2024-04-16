package com.user.restaurantapp.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private String personName;
    private List<OrderItemsDto> orderList;

    private BigDecimal totalPrice;

}
