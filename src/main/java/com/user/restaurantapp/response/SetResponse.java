package com.user.restaurantapp.response;

import com.user.restaurantapp.dto.AddFoodDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Slf4j
public class SetResponse {
    private static final Logger logger = LoggerFactory.getLogger(SetResponse.class.getName());

    public static void setResponseForInvalidInput(AddFoodDto responseDto, String errorMessage) {
        responseDto.setMessage("Failed to add food");
        responseDto.setItem(errorMessage);
    }


    public static void setResponseForFailure(AddFoodDto responseDto, Exception e) {
        responseDto.setMessage("Failed to add food");
        responseDto.setItem("An error occurred while saving food item");
        logger.error("Failed to add food: {}", e.getMessage());
    }

}
