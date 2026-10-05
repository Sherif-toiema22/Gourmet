package com.Gourmet.Gourmet.cart.service;

import com.Gourmet.Gourmet.cart.dto.AddItemToCartRequest;
import com.Gourmet.Gourmet.cart.dto.CartItemIngredientRequest;
import com.Gourmet.Gourmet.cart.entity.Cart;
import com.Gourmet.Gourmet.cart.entity.CartItem;
import com.Gourmet.Gourmet.cart.entity.CartItemIngredient;
import com.Gourmet.Gourmet.cart.repository.CartRepository;
import com.Gourmet.Gourmet.catalog.ingredient.Ingredient;
import com.Gourmet.Gourmet.catalog.ingredient.IngredientPreparationMethod;
import com.Gourmet.Gourmet.catalog.ingredient.repository.IngredientPreparationMethodRepository;
import com.Gourmet.Gourmet.catalog.ingredient.repository.IngredientRepository;
import com.Gourmet.Gourmet.catalog.meal.Meal;
import com.Gourmet.Gourmet.catalog.meal.MealIngredient;
import com.Gourmet.Gourmet.catalog.meal.PreparationMethod;
import com.Gourmet.Gourmet.catalog.meal.repository.MealIngredientRepository;
import com.Gourmet.Gourmet.catalog.meal.repository.MealRepository;
import com.Gourmet.Gourmet.catalog.meal.repository.PreparationMethodRepository;
import com.Gourmet.Gourmet.user.entity.User;
import com.Gourmet.Gourmet.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final MealIngredientRepository mealIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final PreparationMethodRepository preparationMethodRepository;
    private final IngredientPreparationMethodRepository ingredientPreparationMethodRepository;

    public CartService(
            CartRepository cartRepository,
            UserRepository userRepository,
            MealRepository mealRepository,
            MealIngredientRepository mealIngredientRepository,
            IngredientRepository ingredientRepository,
            PreparationMethodRepository preparationMethodRepository,
            IngredientPreparationMethodRepository ingredientPreparationMethodRepository
    ) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.mealRepository = mealRepository;
        this.mealIngredientRepository = mealIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.preparationMethodRepository = preparationMethodRepository;
        this.ingredientPreparationMethodRepository = ingredientPreparationMethodRepository;
    }

    public Cart getOrCreateCart(Long userId) {

        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {

                    User user = userRepository.findById(userId)
                            .orElseThrow(() ->
                                    new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                            );

                    Cart cart = new Cart();
                    cart.setUser(user);

                    return cartRepository.save(cart);
                });
    }

    public Cart addItem(Long userId, AddItemToCartRequest request) {

        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be greater than zero");
        }

        Cart cart = getOrCreateCart(userId);

        Meal meal = mealRepository.findById(request.getMealId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meal not found"));

        if (!meal.isAvailable()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This meal is currently unavailable");
        }

        List<MealIngredient> mealIngredients = mealIngredientRepository.findByMealId(meal.getId());
        Map<Long, MealIngredient> mealIngredientsByIngredientId = mealIngredients.stream()
                .collect(Collectors.toMap(
                        mi -> mi.getIngredient().getId(),
                        Function.identity()
                ));

        List<CartItemIngredientRequest> ingredientRequests = request.getIngredients() != null
                ? request.getIngredients()
                : List.of();

        // every required ingredient of the meal must be present in the request
        for (MealIngredient mealIngredient : mealIngredients) {
            if (mealIngredient.isRequired()) {
                boolean provided = ingredientRequests.stream()
                        .anyMatch(r -> r.getIngredientId().equals(mealIngredient.getIngredient().getId()));
                if (!provided) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Missing required ingredient: " + mealIngredient.getIngredient().getName()
                    );
                }
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setMeal(meal);
        cartItem.setQuantity(request.getQuantity());

        for (CartItemIngredientRequest ingredientRequest : ingredientRequests) {

            MealIngredient mealIngredient = mealIngredientsByIngredientId.get(ingredientRequest.getIngredientId());
            if (mealIngredient == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Ingredient " + ingredientRequest.getIngredientId() + " is not part of this meal"
                );
            }

            Ingredient ingredient = ingredientRepository.findById(ingredientRequest.getIngredientId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient not found"));

            if (!ingredient.isAvailable()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Ingredient " + ingredient.getName() + " is currently unavailable"
                );
            }

            BigDecimal quantity = ingredientRequest.getQuantity() != null
                    ? ingredientRequest.getQuantity()
                    : BigDecimal.ONE;

            if (mealIngredient.getMinQuantity() != null && quantity.compareTo(mealIngredient.getMinQuantity()) < 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Quantity for " + ingredient.getName() + " is below the allowed minimum"
                );
            }
            if (mealIngredient.getMaxQuantity() != null && quantity.compareTo(mealIngredient.getMaxQuantity()) > 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Quantity for " + ingredient.getName() + " exceeds the allowed maximum"
                );
            }

            CartItemIngredient cartItemIngredient = new CartItemIngredient();
            cartItemIngredient.setCartItem(cartItem);
            cartItemIngredient.setIngredient(ingredient);
            cartItemIngredient.setQuantity(quantity);
            cartItemIngredient.setUnitPrice(ingredient.getPricePerUnit());

            // Required ingredients are already covered by the meal's basePrice,
            // so they're not charged again here - only optional ingredients add
            // to the price. A chosen preparation method still adds its surcharge
            // either way (e.g. "well-done" meat costs extra even though the meat
            // itself is required).
            BigDecimal ingredientCost = mealIngredient.isRequired()
                    ? BigDecimal.ZERO
                    : ingredient.getPricePerUnit().multiply(quantity);

            if (ingredientRequest.getPreparationMethodId() != null) {
                PreparationMethod preparationMethod = preparationMethodRepository.findById(ingredientRequest.getPreparationMethodId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Preparation method not found"));

                IngredientPreparationMethod ipm = ingredientPreparationMethodRepository
                        .findByIngredientIdAndPreparationMethodId(ingredient.getId(), preparationMethod.getId())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "This preparation method is not available for " + ingredient.getName()
                        ));

                cartItemIngredient.setPreparationMethod(preparationMethod);
                ingredientCost = ingredientCost.add(ipm.getAdditionalPrice());
            }

            cartItemIngredient.setTotalPrice(ingredientCost);
            cartItem.getIngredients().add(cartItemIngredient);
        }

        cart.getItems().add(cartItem);
        cartRepository.save(cart);

        return cart;
    }

    public Cart updateItemQuantity(Long userId, Long cartItemId, Integer newQuantity) {

        if (newQuantity == null || newQuantity <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be greater than zero");
        }

        Cart cart = getOrCreateCart(userId);

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found"));

        item.setQuantity(newQuantity);
        cartRepository.save(cart);

        return cart;
    }

    public Cart removeItem(Long userId, Long cartItemId) {

        Cart cart = getOrCreateCart(userId);

        boolean removed = cart.getItems().removeIf(item -> item.getId().equals(cartItemId));
        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found");
        }

        cartRepository.save(cart);

        return cart;
    }

    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
