package com.user.restaurantapp.controller;

import com.user.restaurantapp.dto.AddFoodDto;
import com.user.restaurantapp.dto.FoodItemDto;
import com.user.restaurantapp.service.impl.FoodServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/food")
public class FoodController {
    private final FoodServiceImpl foodService;

    public FoodController(FoodServiceImpl foodService) {
        this.foodService = foodService;
    }

    @PostMapping("/add-food")
    @Operation(method = "POST", summary = "Add a new food item", responses = {
            @ApiResponse(responseCode = "200", description = "Food item added successfully",
            content = @Content(schema = @Schema(implementation = FoodItemDto.class),
            examples = @ExampleObject(value = "{\"message\":\"Food added\",\"item\":{\"foodName\":\"Pizza\",\"foodPrice\":10.99}}"))),
            @ApiResponse(responseCode = "400", description = "Failed to add food item")
    })
    ResponseEntity<?> addFood(@RequestBody FoodItemDto item){
        AddFoodDto addFoodDto = foodService.addFoodItem(item);
        if(addFoodDto != null){
            return new ResponseEntity<>(addFoodDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ErrorResponse("Failed to add food"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    @Operation(method = "GET", summary = "Get all food", responses = {
            @ApiResponse(responseCode = "200", description = "List of food",
                    content = @Content(schema = @Schema(implementation = FoodItemDto.class),
                            examples = @ExampleObject(value = "[{\"foodName\":\"Pizza\",\"foodPrice\":10.99},{\"foodName\":\"Burger\",\"foodPrice\":7.99}]"))),
            @ApiResponse(responseCode = "400", description = "Failed getting food list")
    })
    ResponseEntity<?> getFood(){
        List<FoodItemDto> foodItems = foodService.getFoodItems();
        if(foodItems != null){
            return new ResponseEntity<>(foodItems, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ErrorResponse("Failed getting food list"), HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(method = "GET", summary = "Get a food item", responses = {
            @ApiResponse(responseCode = "200", description = "Food found",
                    content = @Content(schema = @Schema(implementation = FoodItemDto.class),
                            examples = @ExampleObject(value = "{\"foodName\":\"Pizza\",\"foodPrice\":10.99}"))),
            @ApiResponse(responseCode = "400", description = "Food not found")
    })
    @GetMapping("/{foodName}")
    ResponseEntity<?> getFoodByFoodName(@PathVariable String foodName){
        List<FoodItemDto> foodItem = foodService.getFoodByName(foodName);
        if(foodItem != null && foodItem.isEmpty()){
            return new ResponseEntity<>(foodItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ErrorResponse("Food not found"), HttpStatus.BAD_REQUEST);
        }
    }

    static class ErrorResponse{
        private String message;
        public ErrorResponse(String message) {
            this.message = message;
        }
    }
}
