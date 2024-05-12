package com.user.restaurantapp.repository;


import com.user.restaurantapp.model.Order;
import com.user.restaurantapp.model.OrderedItems;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void OrderRepository_Save_Test(){
        //arrange
        Order order = new Order();
        OrderedItems orderedItems = new OrderedItems();
        orderedItems.setFoodName("Beans");
        orderedItems.setPortion(1);
        orderedItems.setOrder(order);


        order.setPersonName("John");
        order.setRefNo("12312");
        order.setOrderList(Arrays.asList(orderedItems));

        // act
        Order orderSaved = orderRepository.save(order);


        //assert
        Assertions.assertThat(orderSaved).isNotNull();
    }

    @Test
    public void OrderRepository_GetAll_Test(){
        //arrange
        Order order = new Order();
        OrderedItems orderedItems = new OrderedItems();
        orderedItems.setFoodName("Beans");
        orderedItems.setPortion(1);
        orderedItems.setOrder(order);


        order.setPersonName("John");
        order.setRefNo("12312");
        order.setOrderList(Arrays.asList(orderedItems));

        Order order1 = new Order();
        OrderedItems orderedItems1 = new OrderedItems();
        orderedItems1.setFoodName("Beans");
        orderedItems1.setPortion(1);
        orderedItems1.setOrder(order);


        order1.setPersonName("John");
        order1.setRefNo("12312");
        order1.setOrderList(Arrays.asList(orderedItems));

        // act
        orderRepository.save(order);
        orderRepository.save(order1);
        List<Order> allOrders = orderRepository.findAll();

        //assert
        Assertions.assertThat(allOrders).isNotNull();
        Assertions.assertThat(allOrders.size()).isGreaterThan(1);
    }
}
