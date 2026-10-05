package com.Gourmet.Gourmet.cart.repository;

import com.Gourmet.Gourmet.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
