package com.shanks.E_Commerce.Backend.controller;

import com.shanks.E_Commerce.Backend.dto.AddToCartRequest;
import com.shanks.E_Commerce.Backend.model.CartItem;
import com.shanks.E_Commerce.Backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public List<CartItem> getCartItems(@PathVariable String userId) {
        return cartService.getCartItems(userId);
    }

    @PostMapping("/{userId}")
    public CartItem addToCart(@PathVariable String userId, @RequestBody AddToCartRequest request) {
        return cartService.addToCart(userId, request);
    }

    @PutMapping("/{userId}/{itemId}")
    public CartItem updateCartItem(@PathVariable String userId, @PathVariable String itemId, @RequestBody CartItem item) {
        return cartService.updateCartItem(itemId, item.getQuantity());
    }

    @DeleteMapping("/{userId}/{itemId}")
    public void removeFromCart(@PathVariable String userId, @PathVariable String itemId) {
        cartService.removeFromCart(itemId);
    }

    @DeleteMapping("/{userId}")
    public void clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
    }
}
