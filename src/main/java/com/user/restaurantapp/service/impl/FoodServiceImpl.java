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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.*;

import static javax.swing.SortOrder.ASCENDING;
import static javax.swing.SortOrder.DESCENDING;

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
            logger.info("Food added: " + item.getFoodName() + " " + item.getFoodPrice());
        } catch (Exception e) {
            responseDto.setMessage("Failed to add food");
            responseDto.setItem("An error occurred while saving food item");
            logger.error("Failed to add food: " + e.getMessage(), e);
        }
        return responseDto;
    }

    @Override
    public List<FoodItemDto> getFoodItems( int pageNumber, int pageSize,String sortBy, SortOrder sortOrder) {
        Sort sort;

        switch (sortOrder) {
            case ASCENDING:
                sort = Sort.by(sortBy).ascending();
                break;
            case DESCENDING:
                sort = Sort.by(sortBy).descending();
                break;
            default:
                sort = Sort.by(sortBy);
        }
        PageRequest page = PageRequest.of(pageNumber, pageSize, sort);
        Page<FoodItem> allFoodItems = foodRepository.findAll(page);
        List<FoodItemDto> foodItemDtos = new ArrayList<>();
        for (FoodItem foodItem : allFoodItems){
                foodItemDtos.add(mapToFoodItemDto(foodItem));
        }
        logger.info("Retrieved all foods");
        return foodItemDtos;
    }

    @Override
    public AddFoodDto updateFoodItem(Long id, FoodItemDto item) {
        AddFoodDto responseDto = new AddFoodDto();

        Optional<FoodItem> foodId = foodRepository.findById(id);

        if(foodId.isPresent()){
            FoodItem food = foodId.get();
            if(StringUtils.isBlank(item.getFoodName())){
                food.setFoodName(food.getFoodName());
            }
            if(item.getFoodPrice().compareTo(BigDecimal.ZERO) <= 0) {
                food.setFoodPrice(food.getFoodPrice());
            }

            food.setFoodName(item.getFoodName());
            food.setFoodPrice(item.getFoodPrice());

            responseDto.setMessage("Food details has updated");
            logger.info("Food updated from "+ food + "to " + item );
            responseDto.setItem(item);
            foodRepository.save(food);
        } else {
            logger.error("Food not found else food details did not update with id: " + id + "and: " + item );
            responseDto.setMessage("Food not found else food details did not update");
            responseDto.setItem(null);
        }


        return responseDto;

    }

    @Override
    public FoodItemDto getFoodByName(String foodName) {
        FoodItemDto responseDto = new FoodItemDto();
        Optional<FoodItem> foodItemOptional = foodRepository.findByFoodName(foodName);
        if (foodItemOptional.isEmpty()) {
            log.warn("No food item found with name: {}", foodName);
            return null;
        } else {
            FoodItem foodItem = foodItemOptional.get();
            responseDto.setFoodName(foodItem.getFoodName());
            responseDto.setFoodPrice(foodItem.getFoodPrice());
            logger.info("Got " + responseDto);
            return responseDto;
        }
    }


    private FoodItemDto mapToFoodItemDto(FoodItem foodItem) {
        FoodItemDto foodItemDto = new FoodItemDto();
        foodItemDto.setFoodName(foodItem.getFoodName());
        foodItemDto.setFoodPrice(foodItem.getFoodPrice());
        return foodItemDto;
    }
}
