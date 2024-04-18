package com.user.restaurantapp.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemDto {
//    private static final long serialVersionUID = 2077054515273446470L;

    private Long id;
    private String foodName;
    private BigDecimal foodPrice;
}
