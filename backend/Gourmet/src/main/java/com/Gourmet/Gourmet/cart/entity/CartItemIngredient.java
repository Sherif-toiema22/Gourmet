package com.Gourmet.Gourmet.cart.entity;

import com.Gourmet.Gourmet.catalog.ingredient.Ingredient;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_item_ingredients")
public class CartItemIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CartItem cartItem;

    @ManyToOne
    private Ingredient ingredient;

    private BigDecimal quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;
}