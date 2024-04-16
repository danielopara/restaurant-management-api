package com.user.restaurantapp.service.impl;

import com.user.restaurantapp.dto.AddFoodDto;
import com.user.restaurantapp.dto.FoodItemDto;
import com.user.restaurantapp.model.FoodItem;
import com.user.restaurantapp.repository.FoodRepository;
import com.user.restaurantapp.service.FoodService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private static final Logger logger = LoggerFactory.getLogger(FoodServiceImpl.class.getName());
    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public AddFoodDto addFoodItem(FoodItemDto item) {
        AddFoodDto responseDto  = new AddFoodDto();
        FoodItem newFoodItem = new FoodItem();


        if (StringUtils.isBlank(item.getFoodName())) {
            responseDto.setMessage("Failed to add food");
            responseDto.setItem("Food name cannot be blank");
            logger.warn("Failed to add food: Food name is blank");
            return responseDto;
        }

        if (item.getFoodPrice().compareTo(BigDecimal.ZERO) <= 0) {
            responseDto.setMessage("Failed to add food");
            responseDto.setItem("Food price must be greater than zero");
            logger.warn("Failed to add food: Invalid food price");
            return responseDto;
        }

        Map<String, Object> foodDetails = new HashMap<>();
        foodDetails.put("foodName", item.getFoodName());
        foodDetails.put("foodPrice", item.getFoodPrice());

        responseDto.setMessage("Food added");
        responseDto.setItem(foodDetails);
        try {
            newFoodItem.setFoodName(item.getFoodName());
            newFoodItem.setFoodPrice(item.getFoodPrice());
            foodRepository.save(newFoodItem);
            logger.info("Food added: " + item.getFoodName());
        } catch (Exception e) {
            responseDto.setMessage("Failed to add food");
            responseDto.setItem("An error occurred while saving food item");
            logger.error("Failed to add food: " + e.getMessage(), e);
        }
        return responseDto;
    }

    @Override
    public List<FoodItemDto> getFoodItems() {
        List<FoodItem> allFoodItems = foodRepository.findAll();
        List<FoodItemDto> foodItemDtos = new ArrayList<>();
        for (FoodItem foodItem : allFoodItems){
                foodItemDtos.add(mapToFoodItemDto(foodItem));
        }
        return foodItemDtos;
    }

    @Override
    public List<FoodItemDto> getFoodByName(String foodName) {
        Optional<FoodItem> foodItemOptional = foodRepository.findByFoodName(foodName);
        if (foodItemOptional.isEmpty()) {
            log.warn("No food item found with name: {}", foodName);
            return null;
        } else {
            FoodItem foodItem = foodItemOptional.get();
            FoodItemDto foodItemDto = mapToFoodItemDto(foodItem);
            return Collections.singletonList(foodItemDto);
        }
    }


    private FoodItemDto mapToFoodItemDto(FoodItem foodItem) {
        FoodItemDto foodItemDto = new FoodItemDto();
        foodItemDto.setFoodName(foodItem.getFoodName());
        foodItemDto.setFoodPrice(foodItem.getFoodPrice());
        return foodItemDto;
    }
}
