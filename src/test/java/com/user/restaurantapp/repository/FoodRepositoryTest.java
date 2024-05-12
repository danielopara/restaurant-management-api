package com.user.restaurantapp.repository;

import com.user.restaurantapp.model.FoodItem;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class FoodRepositoryTest {

    @Autowired
    private FoodRepository foodRepository;

    @Test
    public void FoodRepository_Save_Test(){
        //arrange
        FoodItem foodItem = new FoodItem();

        foodItem.setFoodName("Beans");
        foodItem.setFoodPrice(new BigDecimal(10));

        //act
        FoodItem save = foodRepository.save(foodItem);

        //assert
        Assertions.assertThat(save).isNotNull();
        Assertions.assertThat(save.getId()).isGreaterThan(0);

    }

    @Test
    public void FoodRepository_FindAll_Test(){
        //arrange
        FoodItem foodItem = new FoodItem();

        foodItem.setFoodName("Beans");
        foodItem.setFoodPrice(new BigDecimal(10));

        FoodItem foodItem1 = new FoodItem();
        foodItem1.setFoodName("Rice");
        foodItem1.setFoodPrice(new BigDecimal(10));

        foodRepository.save(foodItem);
        foodRepository.save(foodItem1);

        //act
        List<FoodItem> foods = foodRepository.findAll();

        //assertions
        Assertions.assertThat(foods).isNotNull();
        Assertions.assertThat(foods.size()).isEqualTo(2);
    }

    @Test
    public void FoodRepository_FindById_Test(){
        //arrange
        FoodItem foodItem = new FoodItem();

        foodItem.setFoodName("Beans");
        foodItem.setFoodPrice(new BigDecimal(10));
        foodRepository.save(foodItem);
        //act
        Optional<FoodItem> byId = foodRepository.findById(foodItem.getId());
        //assert
        Assertions.assertThat(byId.isPresent()).isTrue();
    }

    @Test
    public void FoodRepository_FindByName_Test(){
        //arrange
        FoodItem foodItem = new FoodItem();

        foodItem.setFoodName("Beans");
        foodItem.setFoodPrice(new BigDecimal(10));
        foodRepository.save(foodItem);
        //act
        Optional<FoodItem> food = foodRepository.findByFoodName(foodItem.getFoodName());
        //assert
        Assertions.assertThat(food.isPresent()).isTrue();
    }
}
