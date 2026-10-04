package com.Gourmet.Gourmet.cart.service;

import com.Gourmet.Gourmet.cart.entity.Cart;
import com.Gourmet.Gourmet.cart.repository.CartRepository;
import com.Gourmet.Gourmet.catalog.ingredient.repository.IngredientRepository;
import com.Gourmet.Gourmet.catalog.meal.repository.MealIngredientRepository;
import com.Gourmet.Gourmet.catalog.meal.repository.MealRepository;
import com.Gourmet.Gourmet.user.entity.User;
import com.Gourmet.Gourmet.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final MealIngredientRepository mealIngredientRepository;
    private final MealRepository mealRepository;
    private final IngredientRepository ingredientRepository;

    public CartService(
            CartRepository cartRepository,
            UserRepository userRepository,
            MealRepository mealRepository,
            MealIngredientRepository mealIngredientRepository,
            IngredientRepository ingredientRepository
    ) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.mealRepository = mealRepository;
        this.mealIngredientRepository = mealIngredientRepository;
        this.ingredientRepository = ingredientRepository;
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