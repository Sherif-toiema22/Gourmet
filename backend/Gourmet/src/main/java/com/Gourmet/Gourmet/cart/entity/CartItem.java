package com.Gourmet.Gourmet.cart.entity;

import com.Gourmet.Gourmet.catalog.meal.Meal;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Meal meal;

    @OneToMany(mappedBy = "cartItem")
    private List<CartItemIngredient> ingredients = new ArrayList<>();

    private Integer quantity;
    @OneToOne
    private CartItemPreparation preparation;
}