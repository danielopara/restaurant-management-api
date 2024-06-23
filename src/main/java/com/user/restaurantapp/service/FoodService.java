package com.user.restaurantapp.service;

import com.user.restaurantapp.dto.AddFoodDto;
import com.user.restaurantapp.dto.FoodItemDto;

import javax.swing.*;
import java.util.List;

public interface FoodService {
//    FoodItem
    AddFoodDto addFoodItem(FoodItemDto item);
    List<FoodItemDto> getFoodItems( int pageNumber, int pageSize, String sortBy, SortOrder sortOrder);
    AddFoodDto updateFoodItem(Long id, FoodItemDto item);
    FoodItemDto getFoodByName(String foodName);
    String deleteFoodItemById(Long id);
}
