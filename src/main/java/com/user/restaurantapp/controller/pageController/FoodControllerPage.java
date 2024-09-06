package com.user.restaurantapp.controller.pageController;

import com.user.restaurantapp.dto.AddFoodDto;
import com.user.restaurantapp.dto.FoodDto;
import com.user.restaurantapp.dto.FoodItemDto;
import com.user.restaurantapp.service.impl.FoodServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.*;
import java.util.List;

@Controller
public class FoodControllerPage {

    private final FoodServiceImpl foodService;

    public FoodControllerPage(FoodServiceImpl foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/")
    public String foodList(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "9") int pageSize,
            @RequestParam(defaultValue = "foodName") String sortBy,
            @RequestParam(defaultValue = "ASCENDING") String sortOrder
            ,Model model){
        SortOrder order = SortOrder.valueOf(sortOrder.toUpperCase());
        List<FoodDto> foodItems = foodService.getFoodItems(pageNumber, pageSize, sortBy, order);
        long foodItemsCount = foodService.getFoodItemsCount();
        int totalPages = (int) Math.ceil((double) foodItemsCount / pageSize);


        model.addAttribute("foodItems", foodItems);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("totalItems", foodItemsCount);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", totalPages);


        return "index";
    }

    @GetMapping("/{foodName}")
    @ResponseBody
    public FoodItemDto getFoodName(Model model, @PathVariable String foodName){
        FoodItemDto foodByName = foodService.getFoodByName(foodName);
        if(foodByName == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No food item found with name: %s", foodName));
        }
        model.addAttribute("foodItems", foodByName);
        return foodByName;
    }

    @GetMapping("/add-food")
    public String addFoodForm(Model model){
        model.addAttribute("foodDto", new FoodDto());
        return "add-food";
    }

    @PostMapping("/addfood")
    public String addFood(Model model, @ModelAttribute FoodDto food){
        AddFoodDto response = foodService.addFoodItem(food);

        if (response.getMessage().equals("Food added")) {
            model.addAttribute("successMessage", "Food item added successfully!");
        } else if(response.getItem().equals("Food name already exists")) {
            model.addAttribute("errorMessage", response.getItem());
        }
        else{
            model.addAttribute("errorMessage", response.getMessage());
        }

        return "add-food";
    }
//    @PutMapping("/update-foodItem/{id}")
//    public String updateFood(@PathVariable Long id, @ModelAttribute FoodDto foodDto, Model model){
//        AddFoodDto updateFood = foodService.updateFoodItem(id, foodDto);
//
//        if(updateFood.getMessage().equals("Food details has updated")){
//            model.addAttribute("successMessage", "Food item updated successfully");
//        } else{
//            model.addAttribute("errorMessage", updateFood.getMessage());
//        }
//
//        return "redirect:/";
//    }
}
