package com.Gourmet.Gourmet.cart.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartItemResponse(
        Long id,
        Long mealId,
        String mealName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal lineTotal,
        List<CartItemIngredientResponse> ingredients
) {
}
