package com.user.restaurantapp.service;

import com.user.restaurantapp.service.foodInterface.FoodCreationService;
import com.user.restaurantapp.service.foodInterface.FoodModificationService;
import com.user.restaurantapp.service.foodInterface.FoodRetrievalService;


public interface FoodService extends FoodCreationService, FoodRetrievalService, FoodModificationService {

}
