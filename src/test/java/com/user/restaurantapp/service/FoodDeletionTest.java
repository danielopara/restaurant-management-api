package com.user.restaurantapp.service;

import com.user.restaurantapp.dto.AddFoodDto;
import com.user.restaurantapp.dto.FoodDto;
import com.user.restaurantapp.model.FoodItem;
import com.user.restaurantapp.repository.FoodRepository;
import com.user.restaurantapp.service.foodInterface.FoodModificationService;
import com.user.restaurantapp.service.impl.FoodServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FoodDeletionTest {

    @Mock
    private FoodRepository foodRepository;

    @InjectMocks
    private FoodServiceImpl foodService;

    private FoodItem foodItem;

    @BeforeEach
    public void setUp(){
        System.out.println("Setting up test...");

        foodItem = new FoodItem();

        foodItem.setId(1L);
        foodItem.setFoodName("Beans");
        foodItem.setFoodPrice(BigDecimal.TEN);
    }

    @AfterEach
    public void afterEachTest(TestInfo test) {
        System.out.println("Test completed successfully: " + test.getDisplayName());
        System.out.println("-----------------------------------------");
    }

    private void mockDeleteFoodItem(Long id){
        when(foodRepository.findById(id)).thenReturn(Optional.of(foodItem));
    }

    @Test
    public void testDeletingFoodItem(){
        Long foodItemId = 1L;
        String expectedResponse = "Food Item: Beans\nwith price: 10 has been deleted";

        mockDeleteFoodItem(foodItemId);

        String actualResponse = foodService.deleteFoodItemById(foodItemId);
        assertEquals(expectedResponse, actualResponse);
    }

}
