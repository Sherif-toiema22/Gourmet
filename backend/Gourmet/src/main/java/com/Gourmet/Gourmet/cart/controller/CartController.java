package com.Gourmet.Gourmet.cart.controller;

import com.Gourmet.Gourmet.cart.dto.AddItemToCartRequest;
import com.Gourmet.Gourmet.cart.dto.CartResponse;
import com.Gourmet.Gourmet.cart.dto.UpdateCartItemQuantityRequest;
import com.Gourmet.Gourmet.cart.mapper.CartMapper;
import com.Gourmet.Gourmet.cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * userId is taken as a path variable for now because the project has no
 * authentication yet. Once Spring Security is added, swap this for the
 * authenticated principal instead of trusting a path variable.
 */
@RestController
@RequestMapping("/api/users/{userId}/cart")
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    public CartController(CartService cartService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }

    @GetMapping
    public CartResponse getCart(@PathVariable Long userId) {
        return cartMapper.toResponse(cartService.getOrCreateCart(userId));
    }

    @PostMapping("/items")
    public CartResponse addItem(
            @PathVariable Long userId,
            @RequestBody AddItemToCartRequest request
    ) {
        return cartMapper.toResponse(cartService.addItem(userId, request));
    }

    @PatchMapping("/items/{itemId}")
    public CartResponse updateItemQuantity(
            @PathVariable Long userId,
            @PathVariable Long itemId,
            @RequestBody UpdateCartItemQuantityRequest request
    ) {
        return cartMapper.toResponse(cartService.updateItemQuantity(userId, itemId, request.quantity()));
    }

    @DeleteMapping("/items/{itemId}")
    public CartResponse removeItem(
            @PathVariable Long userId,
            @PathVariable Long itemId
    ) {
        return cartMapper.toResponse(cartService.removeItem(userId, itemId));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
    }
}
