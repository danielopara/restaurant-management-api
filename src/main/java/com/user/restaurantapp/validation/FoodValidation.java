package com.user.restaurantapp.validation;

import java.math.BigDecimal;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class FoodValidation {
    public static boolean isFoodNameBlank(String foodName) {
        return isBlank(foodName);
    }

    public static boolean isValidFoodPrice(BigDecimal foodPrice) {
        return foodPrice.compareTo(BigDecimal.ZERO) > 0;
    }
}

