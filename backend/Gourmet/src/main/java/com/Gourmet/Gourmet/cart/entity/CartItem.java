package com.Gourmet.Gourmet.cart.entity;

import com.Gourmet.Gourmet.catalog.meal.Meal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    private Integer quantity;

    @OneToMany(
            mappedBy = "cartItem",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CartItemIngredient> ingredients = new ArrayList<>();
}
