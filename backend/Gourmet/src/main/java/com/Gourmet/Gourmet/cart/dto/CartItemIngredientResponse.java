package com.Gourmet.Gourmet.cart.dto;

import java.math.BigDecimal;

public record CartItemIngredientResponse(
        Long id,
        Long ingredientId,
        String ingredientName,
        Long preparationMethodId,
        String preparationMethodName,
        BigDecimal quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice
) {
}
