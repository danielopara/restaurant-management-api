package com.user.restaurantapp.service;

import com.user.restaurantapp.dto.AddFoodDto;
import com.user.restaurantapp.dto.FoodItemDto;
import com.user.restaurantapp.model.FoodItem;

import java.util.List;

public interface FoodService {
//    FoodItem
    AddFoodDto addFoodItem(FoodItemDto item);
    List<FoodItemDto> getFoodItems();
    List<FoodItemDto> getFoodByName(String foodName);
}
