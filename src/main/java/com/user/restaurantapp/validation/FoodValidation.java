package com.user.restaurantapp.validation;

import com.user.restaurantapp.repository.FoodRepository;

import java.math.BigDecimal;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class FoodValidation {

    private final FoodRepository foodRepository;

    public FoodValidation(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public static boolean isFoodNameBlank(String foodName) {
        return isBlank(foodName);
    }

    public static boolean isValidFoodPrice(BigDecimal foodPrice) {
        return foodPrice.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean doesFoodNameExist(String foodName){
        return foodRepository.findByFoodName(foodName).isPresent();
    }
}

