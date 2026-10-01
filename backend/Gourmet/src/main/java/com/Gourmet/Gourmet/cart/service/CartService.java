package com.Gourmet.Gourmet.cart.service;

import com.Gourmet.Gourmet.cart.entity.Cart;
import com.Gourmet.Gourmet.cart.repository.CartRepository;
import com.Gourmet.Gourmet.user.entity.User;
import com.Gourmet.Gourmet.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartService(
            CartRepository cartRepository,
            UserRepository userRepository
    ) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public Cart getOrCreateCart(Long userId) {

        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {

                    User user = userRepository.findById(userId)
                            .orElseThrow(() ->
                                    new RuntimeException("User not found")
                            );

                    Cart cart = new Cart();
                    cart.setUser(user);

                    return cartRepository.save(cart);
                });
    }
}