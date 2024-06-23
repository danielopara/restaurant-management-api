package com.user.restaurantapp.repository;

import com.user.restaurantapp.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<FoodItem, Long> {
//    List<FoodItem> findByFoodName(String foodName);
    Optional<FoodItem> findByFoodName(String foodName);
}
