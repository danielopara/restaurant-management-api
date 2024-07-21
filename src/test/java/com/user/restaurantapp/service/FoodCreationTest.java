package com.user.restaurantapp.service;


import com.user.restaurantapp.dto.AddFoodDto;
import com.user.restaurantapp.dto.FoodDto;
import com.user.restaurantapp.model.FoodItem;
import com.user.restaurantapp.repository.FoodRepository;
import com.user.restaurantapp.service.impl.FoodServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//TODO: Need to fix these test cases

@ExtendWith(MockitoExtension.class)
public class FoodCreationTest {

    @Mock
    private FoodRepository foodRepository;


    @InjectMocks
    private FoodServiceImpl foodCreation;

    private FoodItem foodItem;
    private FoodDto foodDto;


    @BeforeEach
    public void setUp(){
        System.out.println("Setting up test...");
        foodItem = new FoodItem();

        foodItem.setFoodName("Beans");
        foodItem.setFoodPrice(new BigDecimal(10));

        foodDto = new FoodDto();
        foodDto.setFoodName("Beans");
        foodDto.setFoodPrice(new BigDecimal(10));

    }
    @AfterEach
    public void afterEachTest(TestInfo testInfo) {
        String displayName = testInfo.getDisplayName();
        String testClass = testInfo.getTestClass().map(Class::getName).orElse("Unknown Class");
        String testMethod = testInfo.getTestMethod().map(Method::getName).orElse("Unknown Method");

        if (testInfo.getTags().contains("failed")) {
            System.out.println("Test failed: " + displayName);
            System.out.println("Test class: " + testClass);
            System.out.println("Test method: " + testMethod);
        } else {
            System.out.println("Test completed successfully: " + displayName);
            System.out.println("Test class: " + testClass);
            System.out.println("Test method: " + testMethod);
        }
        System.out.println("-----------------------------------------");
    }

    @Test
    public void testFoodsCreation() {
        when(foodRepository.save(any(FoodItem.class))).thenReturn(foodItem);

        AddFoodDto actualResponse = foodCreation.addFoodItem(foodDto);

        assertEquals("Food added", actualResponse.getMessage());
        verify(foodRepository, times(1)).save(any(FoodItem.class));
    }

    @Test
    public void testFoodCreation_FoodNameIsBlank(){
        foodDto.setFoodName("");
        lenient().when(foodRepository.save(any(FoodItem.class))).thenReturn(foodItem);

        AddFoodDto actualResponse = foodCreation.addFoodItem(foodDto);

        assertEquals("Failed to add food", actualResponse.getMessage());
        verify(foodRepository, never()).save(any(FoodItem.class));
    }

    @Test
    public void testFoodCreation_FoodPriceIsBlank(){
        foodDto.setFoodPrice(new BigDecimal(0));

        lenient().when(foodRepository.save(any(FoodItem.class))).thenReturn(foodItem);

        AddFoodDto actualResponse = foodCreation.addFoodItem(foodDto);

        assertEquals("Failed to add food", actualResponse.getMessage());
        verify(foodRepository, never()).save(any(FoodItem.class));


    }

    @Test
    public void testFoodCreation_FoodPriceIsNull(){
        AddFoodDto expectedResponse = new AddFoodDto();
        foodDto.setFoodPrice(null);

        lenient().when(foodRepository.save(any(FoodItem.class))).thenReturn(foodItem);

        AddFoodDto actualResponse = foodCreation.addFoodItem(foodDto);

        assertEquals("Failed to add food", actualResponse.getMessage());
        verify(foodRepository, never()).save(any(FoodItem.class));

    }
}
