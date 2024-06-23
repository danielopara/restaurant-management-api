package com.user.restaurantapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ordered_items")
public class OrderedItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String foodName;
    private int portion;
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;
}
