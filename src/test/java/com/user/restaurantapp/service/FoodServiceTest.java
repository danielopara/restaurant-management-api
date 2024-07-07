package com.user.restaurantapp.service;

import com.user.restaurantapp.dto.AddFoodDto;
import com.user.restaurantapp.dto.FoodDto;
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
import org.springframework.data.domain.*;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.List;
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

        FoodDto foodItemDto = new FoodDto();
        foodItemDto.setFoodPrice(new BigDecimal(10));
        foodItemDto.setFoodName("Beans");

        //act
        when(foodRepository.save(Mockito.any(FoodItem.class))).thenReturn(foodItem);
        AddFoodDto addFoodDto = foodService.addFoodItem(foodItemDto);

        //assert
        Assertions.assertThat(addFoodDto).isNotNull();
        assertEquals("Food added",addFoodDto.getMessage());
        assertTrue(addFoodDto.getItem() instanceof Map);
    }

    @Test
    public void FoodService_TestBlankNameAndWrongPrice(){
        //test blank name
        //arrange
        FoodDto foodItemDto = new FoodDto();
        foodItemDto.setFoodPrice(new BigDecimal(10));

        //act
//        when(foodRepository.save(Mockito.any(FoodItem.class))).thenReturn(foodItem);
        AddFoodDto addFoodDto = foodService.addFoodItem(foodItemDto);

        //assert
        assertNotNull(addFoodDto);
        assertEquals("Food name cannot be blank", addFoodDto.getItem());
        assertEquals("Failed to add food", addFoodDto.getMessage());

        //test wrong price
        //arrange
        FoodDto foodItem2 = new FoodDto();
        foodItem2.setFoodPrice(new BigDecimal(-1));
        foodItem2.setFoodName("Yam");

        //act
        AddFoodDto addFoodDto2 = foodService.addFoodItem(foodItem2);

        //assert
        assertNotNull(addFoodDto2);
        assertEquals("Food price must be greater than zero", addFoodDto2.getItem());
        assertEquals("Failed to add food", addFoodDto2.getMessage());
    }



    @Test
    public void testGetFoodItems() {
        // Arrange
        int pageNumber = 0;
        int pageSize = 5;
        String sortBy = "foodName";
        SortOrder sortOrder = SortOrder.ASCENDING;

        // Mock the foodRepository to return a predefined list of FoodItem entities
        FoodItem foodItem1 = new FoodItem();
        foodItem1.setFoodName("Pizza");
        foodItem1.setFoodPrice(new BigDecimal(10));

        FoodItem foodItem2 = new FoodItem();
        foodItem2.setFoodName("Burger");
        foodItem2.setFoodPrice(new BigDecimal(8));

        Page<FoodItem> foodItemsPage = new PageImpl<>(List.of(foodItem1, foodItem2));
        when(foodRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)))).thenReturn(foodItemsPage);

        // Act
        List<FoodDto> foodItemDtos = foodService.getFoodItems(pageNumber, pageSize, sortBy, sortOrder);

        // Assert
        assertEquals(2, foodItemDtos.size());
        assertEquals("Pizza", foodItemDtos.get(0).getFoodName());
        assertEquals("Burger", foodItemDtos.get(1).getFoodName());
    }

}
