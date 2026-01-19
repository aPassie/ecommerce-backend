package com.shanks.E_Commerce.Backend.service;

import com.shanks.E_Commerce.Backend.dto.AddToCartRequest;
import com.shanks.E_Commerce.Backend.model.CartItem;
import com.shanks.E_Commerce.Backend.model.Product;
import com.shanks.E_Commerce.Backend.repository.CartItemRepository;
import com.shanks.E_Commerce.Backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<CartItem> getCartItems(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public CartItem addToCart(String userId, AddToCartRequest request) {
        if (request.getProductId() == null || request.getQuantity() <= 0) {
            return null;
        }

        Product product = productRepository.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return null;
        }

        if (product.getStock() < request.getQuantity()) {
            return null;
        }

        CartItem item = new CartItem();
        item.setUserId(userId);
        item.setProductId(request.getProductId());
        item.setQuantity(request.getQuantity());
        return cartItemRepository.save(item);
    }

    public CartItem updateCartItem(String itemId, int quantity) {
        CartItem existing = cartItemRepository.findById(itemId).orElse(null);
        if (existing != null) {
            existing.setQuantity(quantity);
            return cartItemRepository.save(existing);
        }
        return null;
    }

    public void removeFromCart(String itemId) {
        cartItemRepository.deleteById(itemId);
    }

    public void clearCart(String userId) {
        List<CartItem> items = cartItemRepository.findByUserId(userId);
        cartItemRepository.deleteAll(items);
    }
}
