package com.user.restaurantapp.service.foodInterface;

import com.user.restaurantapp.dto.FoodDto;
import com.user.restaurantapp.dto.FoodItemDto;

import javax.swing.*;
import java.util.List;

public interface FoodRetrievalService{
    List<FoodDto> getFoodItems(int pageNumber, int pageSize, String sortBy, SortOrder sortOrder);
    FoodItemDto getFoodByName(String foodName);
}
