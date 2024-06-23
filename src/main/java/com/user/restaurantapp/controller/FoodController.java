package com.user.restaurantapp.controller;

import com.user.restaurantapp.config.LoggingUtil;
import com.user.restaurantapp.dto.AddFoodDto;
import com.user.restaurantapp.dto.FoodItemDto;
import com.user.restaurantapp.service.impl.FoodServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnegative;
import javax.swing.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/food")
@Tag(name = "Food Service", description = "Services that belongs to Food items management")
@CrossOrigin(origins = "*")
public class FoodController {
    private final FoodServiceImpl foodService;
    private static final Logger logger = LoggerFactory.getLogger(FoodController.class);

    public FoodController(FoodServiceImpl foodService) {
        this.foodService = foodService;
    }

    private void logRequest( HttpServletRequest request) {
        logger.info("Received a request from: {}", request.getRequestURI());
    }
    private void logError(HttpServletRequest request){
        logger.error("Food not found {} \n Endpoint :{}" , HttpStatus.BAD_REQUEST , request.getRequestURI());
    }


    @PostMapping("/add-food")
    @Operation(method = "POST", summary = "Add a new food item", responses = {
            @ApiResponse(responseCode = "200", description = "Food item added successfully",
            content = @Content(schema = @Schema(implementation = FoodItemDto.class),
            examples = @ExampleObject(value = "{\"message\":\"Food added\",\"item\":{\"foodName\":\"Pizza\",\"foodPrice\":10.99}}"))),
            @ApiResponse(responseCode = "400", description = "Failed to add food item")
    })
    ResponseEntity<?> addFood(@RequestBody FoodItemDto item, HttpServletRequest request){
        AddFoodDto addFoodDto = foodService.addFoodItem(item);
        logRequest(request);
        if(addFoodDto != null){
            return new ResponseEntity<>(addFoodDto, HttpStatus.OK);
        } else {
            logError(request);
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
    ResponseEntity<?> getFood(@RequestParam(defaultValue = "0") @Nonnegative Integer pageNumber,
                              @RequestParam(defaultValue = "10") @Nonnegative Integer pageSize,
                              @RequestParam(defaultValue = "id") String sortBy,
                              @RequestParam(defaultValue = "ASCENDING") SortOrder sortOrder,
                              HttpServletRequest request){
        String requestURI = request.getRequestURI();
        logRequest(request);
        List<FoodItemDto> foodItems = foodService.getFoodItems(pageNumber, pageSize, sortBy, sortOrder);
        if(foodItems != null){
            return new ResponseEntity<>(foodItems, HttpStatus.OK);
        } else {
            logError(request);
            return new ResponseEntity<>(new ErrorResponse("Failed getting food list"), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update-food/{id}")
    @Operation(method = "PUT", summary = "Update food details", responses = {
            @ApiResponse(responseCode = "200", description = "Updating food details of a particular item",
            content = @Content(schema = @Schema(implementation = FoodItemDto.class),
                    examples = @ExampleObject(value = "[{\"foodName\":\"Pizza\",\"foodPrice\":10.99}"))),
            @ApiResponse(responseCode = "400", description = "Failed updating food details")
    })
    ResponseEntity<?> updatingFood(@PathVariable Long id, @RequestBody FoodItemDto item, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        logRequest(request);
        AddFoodDto addFoodDto = foodService.updateFoodItem(id, item);
        if(addFoodDto != null){
            return new ResponseEntity<>(addFoodDto, HttpStatus.OK);
        } else {
            logError(request);
            return new ResponseEntity<>(new ErrorResponse("Failed updating food details"), HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(method = "GET", summary = "Get a food item", responses = {
            @ApiResponse(responseCode = "200", description = "Food found",
                    content = @Content(schema = @Schema(implementation = FoodItemDto.class),
                            examples = @ExampleObject(value = "{\"foodName\":\"Pizza\",\"foodPrice\":10.99}"))),
            @ApiResponse(responseCode = "400", description = "Food not found")
    })
    @GetMapping("/{foodName}")
    ResponseEntity<?> getFoodByFoodName(@PathVariable String foodName, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        logRequest(request);
        FoodItemDto foodItem = foodService.getFoodByName(foodName);
        if(foodItem != null){
            return new ResponseEntity<>(foodItem, HttpStatus.OK);
        } else {
            logError(request);
            return new ResponseEntity<>(new ErrorResponse("Food not found"), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(method = "GET", summary = "Delete a food item", responses = {
            @ApiResponse(responseCode = "200", description = "Food deleted"),
            @ApiResponse(responseCode = "400", description = "Food not deleted")
    })
    @DeleteMapping("/delete-food/{id}")
    ResponseEntity<?> delete_food (@PathVariable Long id, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        logRequest(request);
        String foodItem = foodService.deleteFoodItemById(id);
        if(foodItem != null){
            return new ResponseEntity<>(foodItem, HttpStatus.OK);
        } else {
            logError(request);
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
