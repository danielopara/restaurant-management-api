package com.user.restaurantapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "food_item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String foodName;
    private BigDecimal foodPrice;
}
