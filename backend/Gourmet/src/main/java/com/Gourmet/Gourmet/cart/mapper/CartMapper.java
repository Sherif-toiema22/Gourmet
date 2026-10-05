package com.Gourmet.Gourmet.cart.mapper;

import com.Gourmet.Gourmet.cart.dto.CartItemIngredientResponse;
import com.Gourmet.Gourmet.cart.dto.CartItemResponse;
import com.Gourmet.Gourmet.cart.dto.CartResponse;
import com.Gourmet.Gourmet.cart.entity.Cart;
import com.Gourmet.Gourmet.cart.entity.CartItem;
import com.Gourmet.Gourmet.cart.entity.CartItemIngredient;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CartMapper {

    public CartItemIngredientResponse toResponse(CartItemIngredient cartItemIngredient) {

        Long preparationMethodId = cartItemIngredient.getPreparationMethod() != null
                ? cartItemIngredient.getPreparationMethod().getId()
                : null;

        String preparationMethodName = cartItemIngredient.getPreparationMethod() != null
                ? cartItemIngredient.getPreparationMethod().getName()
                : null;

        return new CartItemIngredientResponse(
                cartItemIngredient.getId(),
                cartItemIngredient.getIngredient().getId(),
                cartItemIngredient.getIngredient().getName(),
                preparationMethodId,
                preparationMethodName,
                cartItemIngredient.getQuantity(),
                cartItemIngredient.getUnitPrice(),
                cartItemIngredient.getTotalPrice()
        );
    }

    public CartItemResponse toResponse(CartItem cartItem) {

        List<CartItemIngredientResponse> ingredients = cartItem.getIngredients().stream()
                .map(this::toResponse)
                .toList();

        // unit price for one serving of this item = the meal's base price
        // plus whatever each chosen ingredient line added on top of it
        BigDecimal ingredientsExtra = ingredients.stream()
                .map(CartItemIngredientResponse::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal unitPrice = cartItem.getMeal().getBasePrice().add(ingredientsExtra);
        BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity()));

        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getMeal().getId(),
                cartItem.getMeal().getName(),
                cartItem.getQuantity(),
                unitPrice,
                lineTotal,
                ingredients
        );
    }

    public CartResponse toResponse(Cart cart) {

        List<CartItemResponse> items = cart.getItems().stream()
                .map(this::toResponse)
                .toList();

        BigDecimal totalPrice = items.stream()
                .map(CartItemResponse::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(
                cart.getId(),
                cart.getUser().getId(),
                items,
                totalPrice
        );
    }
}
