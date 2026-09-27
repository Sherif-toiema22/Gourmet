package com.Gourmet.Gourmet.cart.entity;

import com.Gourmet.Gourmet.cart.entity.CartItem;
import com.Gourmet.Gourmet.catalog.meal.PreparationMethod;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_item_preparations")
public class CartItemPreparation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private CartItem cartItem;

    @ManyToOne
    private PreparationMethod preparationMethod;

    private BigDecimal price;
}