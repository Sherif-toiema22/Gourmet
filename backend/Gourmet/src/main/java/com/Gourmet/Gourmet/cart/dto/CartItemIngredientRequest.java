package com.Gourmet.Gourmet.cart.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartItemIngredientRequest {

    private Long ingredientId;

    private BigDecimal quantity;
}