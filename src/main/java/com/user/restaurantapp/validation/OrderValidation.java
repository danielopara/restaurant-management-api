package com.user.restaurantapp.validation;

import lombok.Getter;

import java.util.List;

public class OrderValidation {
    @Getter
    private boolean valid;
    private List<String> missingItems;

    public OrderValidation(boolean valid, List<String> missingItems) {
        this.valid = valid;
        this.missingItems = missingItems;
    }

}
