package com.user.restaurantapp.service;


import com.user.restaurantapp.dto.AddFoodDto;
import com.user.restaurantapp.dto.FoodDto;
import com.user.restaurantapp.model.FoodItem;
import com.user.restaurantapp.repository.FoodRepository;
import com.user.restaurantapp.service.foodInterface.FoodCreationService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;


@ExtendWith(MockitoExtension.class)
public class FoodCreationTest {

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private FoodCreationService foodCreation;

    private FoodItem foodItem;
    private FoodDto foodDto;

    private void mockFoodCreationService(AddFoodDto expectedResponse) {
        when(foodCreation.addFoodItem(any(FoodDto.class))).thenReturn(expectedResponse);
    }

    private void assertFoodDto(AddFoodDto expectedResponse, AddFoodDto actualResponse){
        assertEquals(expectedResponse, actualResponse);
    }

    @BeforeEach
    public void SetUp(){
        foodItem = new FoodItem();

        foodItem.setFoodName("Beans");
        foodItem.setFoodPrice(new BigDecimal(10));

        foodDto = new FoodDto();
        foodDto.setFoodPrice(new BigDecimal(10));
        foodItem.setFoodName("Beans");

    }
    @AfterEach
    public void afterEachTest() {
        System.out.println("Test completed successfully.");
    }

    @Test
    public void testFoodCreation(){
        AddFoodDto expectedResponse = new AddFoodDto();
        mockFoodCreationService(expectedResponse);

        AddFoodDto actualResponse = foodCreation.addFoodItem(foodDto);

        assertFoodDto(expectedResponse, actualResponse);
        verify(foodCreation, times(1)).addFoodItem(foodDto);
    }

    @Test
    public void testFoodCreation_FoodNameIsBlank(){
        AddFoodDto expectedResponse = new AddFoodDto();
        foodDto.setFoodName("");
        mockFoodCreationService(expectedResponse);

        AddFoodDto actualResponse = foodCreation.addFoodItem(foodDto);

        assertFoodDto(expectedResponse, actualResponse);
        verify(foodCreation, times(1)).addFoodItem(foodDto);
    }

    @Test
    public void testFoodCreation_FoodPriceIsBlank(){
        AddFoodDto expectedResponse = new AddFoodDto();
        foodDto.setFoodPrice(new BigDecimal(0));

        mockFoodCreationService(expectedResponse);

        AddFoodDto actualResponse = foodCreation.addFoodItem(foodDto);

        assertFoodDto(expectedResponse, actualResponse);
        verify(foodCreation, times(1)).addFoodItem(foodDto);
    }

    @Test
    public void testFoodCreation_FoodPriceIsNull(){
        AddFoodDto expectedResponse = new AddFoodDto();
        foodDto.setFoodPrice(null);

        mockFoodCreationService(expectedResponse);

        AddFoodDto actualResponse = foodCreation.addFoodItem(foodDto);

        assertFoodDto(expectedResponse, actualResponse);
        verify(foodCreation, times(1)).addFoodItem(foodDto);
    }
}
