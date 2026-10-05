package com.Gourmet.Gourmet.cart.repository;

import com.Gourmet.Gourmet.cart.entity.CartItemIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemIngredientRepository extends JpaRepository<CartItemIngredient, Long> {
}
