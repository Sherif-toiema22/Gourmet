package com.Gourmet.Gourmet.cart.entity;

import com.Gourmet.Gourmet.catalog.ingredient.Ingredient;
import com.Gourmet.Gourmet.catalog.meal.PreparationMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_item_ingredients")
@Getter
@Setter
public class CartItemIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_item_id", nullable = false)
    private CartItem cartItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    /**
     * Back to being per-ingredient: null means this ingredient was chosen
     * with no specific preparation method.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preparation_method_id")
    private PreparationMethod preparationMethod;

    private BigDecimal quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;
}
