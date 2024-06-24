package com.user.restaurantapp.service.foodInterface;

import com.user.restaurantapp.dto.AddFoodDto;
import com.user.restaurantapp.dto.FoodDto;

public interface FoodCreationService {
    AddFoodDto addFoodItem(FoodDto food);
}

