package com.user.restaurantapp.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Optional;

import com.user.restaurantapp.dto.AddFoodDto;
import com.user.restaurantapp.dto.FoodDto;
import com.user.restaurantapp.model.FoodItem;
import com.user.restaurantapp.repository.FoodRepository;
import com.user.restaurantapp.service.impl.FoodServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FoodUpdateTest {
    @Mock
    private FoodRepository foodRepository;


    @InjectMocks
    private FoodServiceImpl foodService;

    private FoodItem foodItem;
    private FoodDto foodDto;

    @BeforeEach
    public void setUp(){
        foodItem = new FoodItem();
        foodItem.setId(1L);
        foodItem.setFoodName("Original Name");
        foodItem.setFoodPrice(BigDecimal.TEN);

        foodDto = new FoodDto();
        foodDto.setFoodName("Updated Name");
        foodDto.setFoodPrice(BigDecimal.ONE);
    }

    @Test
    public void testUpdateFoodItem_FoodExists(){
        when(foodRepository.findById(eq(1L))).thenReturn(Optional.of(foodItem));

        AddFoodDto response = foodService.updateFoodItem(1L, foodDto);

        assertEquals("Food details has updated", response.getMessage());
        assertEquals(foodDto, response.getItem());

        verify(foodRepository, times(1)).findById(eq(1L));
        verify(foodRepository, times(1)).save(any(FoodItem.class));
//        verify(logger, times(1)).info(eq("Food updated from {} to {}"), eq(foodItem), eq(foodDto));
    }

    @Test
    public void testUpdateFoodItem_BlankFoodName() {
        foodDto.setFoodName("");
        when(foodRepository.findById(eq(1L))).thenReturn(Optional.of(foodItem));

        AddFoodDto response = foodService.updateFoodItem(1L, foodDto);

        assertEquals("no food name", response.getMessage());

//        assertEquals("Food details has updated", response.getMessage());
//        assertEquals(foodDto, response.getItem());
//        assertEquals("Original Name", foodItem.getFoodName());

//        verify(foodRepository, times(1)).findById(eq(1L));
//        verify(foodRepository, times(1)).save(any(FoodItem.class));
//        verify(logger, times(1)).info(anyString(), eq(foodItem), eq(foodDto));
    }

    @Test
    public void testUpdateFoodItem_FoodPriceLessThanZero(){
        foodDto.setFoodPrice(new BigDecimal(-1));
        when(foodRepository.findById(eq(1L))).thenReturn(Optional.of(foodItem));

        AddFoodDto response = foodService.updateFoodItem(1L, foodDto);

        assertEquals("food price cannot be less than 0", response.getMessage());
    }

    @Test
    public void testUpdateFoodItem_FoodNotFound() {
        when(foodRepository.findById(eq(1L))).thenReturn(Optional.empty());

        AddFoodDto response = foodService.updateFoodItem(1L, foodDto);

        assertEquals("Food not found else food details did not update", response.getMessage());
        assertNull(response.getItem());

        verify(foodRepository, times(1)).findById(eq(1L));
        verify(foodRepository, times(0)).save(any(FoodItem.class));
//        verify(logger, times(1)).error(anyString(), eq(1L), eq(foodDto));
    }
}
