package com.Gourmet.Gourmet.cart.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddItemToCartRequest {

    private Long mealId;

    private Integer quantity;

    private List<CartItemIngredientRequest> ingredients;

    private Long preparationMethodId;
}