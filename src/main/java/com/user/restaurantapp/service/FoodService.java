package com.user.restaurantapp.service;

import com.user.restaurantapp.dto.AddFoodDto;
import com.user.restaurantapp.dto.FoodItemDto;
import com.user.restaurantapp.model.FoodItem;
import org.springframework.data.domain.Sort;

import javax.swing.*;
import java.util.List;

public interface FoodService {
//    FoodItem
    AddFoodDto addFoodItem(FoodItemDto item);
    List<FoodItemDto> getFoodItems(String sortBy, SortOrder sortOrder);
    List<FoodItemDto> getFoodByName(String foodName);
}
