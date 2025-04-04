package com.andrew.cart_service.controller;

import com.andrew.cart_service.dto.ApiResponse;
import com.andrew.cart_service.dto.CartItemRequest;
import com.andrew.cart_service.entity.Cart;
import com.andrew.cart_service.entity.CartItem;
import com.andrew.cart_service.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {


    private final CartService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Cart>>> getAllCarts(){
        List<Cart> carts = service.getAllCarts();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("All carts: ",
                        HttpStatus.OK.value(),
                        LocalDateTime.now(),
                        carts));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<Cart>>> getAllCartsByUserId(@PathVariable Long userId){
        List<Cart> carts = service.getAllCartsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Carts for user id: " + userId,
                        HttpStatus.OK.value(),
                        LocalDateTime.now(),
                        carts));
    }

    @GetMapping("/{cartId}/items")
    public ResponseEntity<ApiResponse<List<CartItem>>> getAllCartItems(@PathVariable Long cartId){
        List<CartItem> cartItems = service.getAllCartItems(cartId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Cart items for cart id: " + cartId,
                        HttpStatus.OK.value(),
                        LocalDateTime.now(),
                        cartItems));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<Cart>> createCart(@RequestBody @Valid Long userId){
        Cart cart = service.createCart(userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Cart created successfully!",
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now(),
                        cart));
    }


    @PostMapping("/{cartId}/items")
    public ResponseEntity<ApiResponse<CartItem>> createItem(@PathVariable Long cartId, @RequestBody @Valid CartItemRequest request){
        CartItem cartItem = service.createItem(cartId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Cart item created successfully!",
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now(),
                        cartItem));
    }


    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponse<Cart>> deleteCartsByCartId(@PathVariable Long cartId){
        service.deleteCartsByCartId(cartId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Cart deleted successfully!",
                        HttpStatus.OK.value(),
                        LocalDateTime.now(),
                        null));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Cart>> deleteCartsByUserId(@PathVariable Long userId){
        service.deleteCartsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Carts deleted successfully!",
                        HttpStatus.OK.value(),
                        LocalDateTime.now(),
                        null));
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<ApiResponse<CartItem>> deleteCartItem(@PathVariable Long cartId, @PathVariable Long productId){
        service.deleteCartItem(cartId,productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Cart Item deleted successfully!",
                        HttpStatus.OK.value(),
                        LocalDateTime.now(),
                        null));
    }

}
