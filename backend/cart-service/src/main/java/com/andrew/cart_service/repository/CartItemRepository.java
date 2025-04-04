package com.andrew.cart_service.repository;

import com.andrew.cart_service.entity.Cart;
import com.andrew.cart_service.entity.CartItem;
import com.andrew.cart_service.entity.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
    List<CartItem> findByCart(Cart cart);
    void deleteByProductId(Long productId);

    boolean existsByProductId(Long productId);
}
