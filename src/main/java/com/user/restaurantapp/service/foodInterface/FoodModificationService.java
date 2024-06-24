package com.user.restaurantapp.service.foodInterface;

import com.user.restaurantapp.dto.AddFoodDto;
import com.user.restaurantapp.dto.FoodDto;

public interface FoodModificationService {
    AddFoodDto updateFoodItem(Long id, FoodDto item);
    String deleteFoodItemById(Long id);
}
