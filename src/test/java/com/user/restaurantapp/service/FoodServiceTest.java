package com.user.restaurantapp.service;

import com.user.restaurantapp.dto.AddFoodDto;
import com.user.restaurantapp.dto.FoodItemDto;
import com.user.restaurantapp.model.FoodItem;
import com.user.restaurantapp.repository.FoodRepository;
import com.user.restaurantapp.service.impl.FoodServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FoodServiceTest {
    @Mock
    private FoodRepository foodRepository;
    @InjectMocks
    private FoodServiceImpl foodService;

    @Test
    public void FoodService_CreateFood_Test(){
        //arrange
        FoodItem foodItem = new FoodItem();

        foodItem.setFoodName("Beans");
        foodItem.setFoodPrice(new BigDecimal(10));

        FoodItemDto foodItemDto = new FoodItemDto();
        foodItemDto.setFoodPrice(new BigDecimal(10));
        foodItemDto.setFoodName("Beans");

        //act
        when(foodRepository.save(Mockito.any(FoodItem.class))).thenReturn(foodItem);
        AddFoodDto addFoodDto = foodService.addFoodItem(foodItemDto);
        Assertions.assertThat(addFoodDto).isNotNull();
        assertEquals("Food added",addFoodDto.getMessage());
        assertTrue(addFoodDto.getItem() instanceof Map);
    }
}
