package com.user.restaurantapp.service.impl;

import com.user.restaurantapp.dto.AddFoodDto;
import com.user.restaurantapp.dto.FoodDto;
import com.user.restaurantapp.dto.FoodItemDto;
import com.user.restaurantapp.model.FoodItem;
import com.user.restaurantapp.repository.FoodRepository;
import com.user.restaurantapp.service.FoodService;
import com.user.restaurantapp.validation.FoodValidation;
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

import static org.apache.commons.lang3.StringUtils.isBlank;


@Service
@Slf4j
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private static final Logger logger = LoggerFactory.getLogger(FoodServiceImpl.class.getName());
    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }



    private boolean isFoodNameBlank(String foodName) {
        return isBlank(foodName);
    }

    private void setResponseForInvalidInput(AddFoodDto responseDto, String errorMessage) {
        responseDto.setMessage("Failed to add food");
        responseDto.setItem(errorMessage);
    }

    private boolean isValidFoodPrice(BigDecimal foodPrice) {
        return foodPrice.compareTo(BigDecimal.ZERO) > 0;
    }

    private void setResponseForFailure(AddFoodDto responseDto, Exception e) {
        responseDto.setMessage("Failed to add food");
        responseDto.setItem("An error occurred while saving food item");
        logger.error("Failed to add food: {}", e.getMessage());
    }



    @Override
    public AddFoodDto addFoodItem(FoodDto item) {
            AddFoodDto responseDto  = new AddFoodDto();
            FoodItem newFoodItem = new FoodItem();

        if (FoodValidation.isFoodNameBlank(item.getFoodName())) {
            setResponseForInvalidInput(responseDto, "Food name cannot be blank");
            logger.warn("Failed to add food: Food name is blank");
            return responseDto;
        }

        if(item.getFoodPrice() == null){
            setResponseForInvalidInput(responseDto, "food price cannot be null");
            return responseDto;
        }

        if (!FoodValidation.isValidFoodPrice(item.getFoodPrice())) {
            setResponseForInvalidInput(responseDto, "Food price must be greater than zero");
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
                logger.info("Food added: {} {}" , item.getFoodName() , item.getFoodPrice());
            } catch (Exception e) {
                setResponseForFailure(responseDto, e);
            }
            return responseDto;

    }

    @Override
//    @Cacheable(value = "FoodItemDto", key = "#pageNumber + '|' + #pageSize + '|' + #sortBy + '|' + #sortOrder")
    public List<FoodItemDto> getFoodItems( int pageNumber, int pageSize,String sortBy, SortOrder sortOrder) {
        List<FoodItemDto> foodItemDtos = new ArrayList<>();
        try{
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

            for (FoodItem foodItem : allFoodItems){
                foodItemDtos.add(mapToFoodItemDto(foodItem));
            }
            logger.info("Retrieved all foods");
            System.out.println("gotten from db");
        } catch (Exception e){
            logger.error("Error retrieving food items: ", e);
        }
        return foodItemDtos;
    }

    @Override
    public AddFoodDto updateFoodItem(Long id, FoodDto item) {
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
            logger.info("Food updated from {} to {}", food, item );
            responseDto.setItem(item);
            foodRepository.save(food);
        } else {
            logger.error("Food not found else food details did not update with id: {} and {}" , id , item );
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
            logger.info("Got {}" , responseDto);
            return responseDto;
        }
    }

    @Override
    public String deleteFoodItemById(Long id) {
        Optional<FoodItem> foodItem = foodRepository.findById(id);
        if(foodItem.isPresent()){
            foodRepository.deleteById(id);
            logger.info("Food Item: {} \nwith price: {}" , foodItem.get().getFoodName() ,
                     foodItem.get().getFoodPrice() );
            return "Food Item: " + foodItem.get().getFoodName() +
                    "\nwith price: " + foodItem.get().getFoodPrice() +" has been deleted";
        }
        logger.error("Failed to delete Food item with id: {} \nit does not exist",id);
        return "Failed to delete Food item with id: " +id + "\nit does not exist";
    }


    private FoodItemDto mapToFoodItemDto(FoodItem foodItem) {
        FoodItemDto foodItemDto = new FoodItemDto();
        foodItemDto.setId(foodItem.getId());
        foodItemDto.setFoodName(foodItem.getFoodName());
        foodItemDto.setFoodPrice(foodItem.getFoodPrice());
        return foodItemDto;
    }
}
