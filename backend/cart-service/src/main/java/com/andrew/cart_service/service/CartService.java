package com.andrew.cart_service.service;

import com.andrew.cart_service.dto.ApiResponse;
import com.andrew.cart_service.dto.CartItemRequest;
import com.andrew.cart_service.entity.Cart;
import com.andrew.cart_service.entity.CartItem;
import com.andrew.cart_service.entity.CartItemId;
import com.andrew.cart_service.entity.Product;
import com.andrew.cart_service.exception.CartNotFoundException;
import com.andrew.cart_service.exception.ProductNotFoundException;
import com.andrew.cart_service.repository.CartItemRepository;
import com.andrew.cart_service.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository repository;
    private final CartItemRepository cartItemRepository;
    private final RestTemplate restTemplate;
    private static final String PRODUCT_SERVICE_URL = "http://localhost:8090/api/v1/products/";


    public List<CartItem> getAllCartItems(Long cartId) {
        Cart cart = repository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with card id : " + cartId));

        return cartItemRepository.findByCart(cart);
    }

    public List<Cart> getAllCartsByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }

    public List<Cart> getAllCarts() {
        return repository.findAll();
    }

    @Transactional
    public Cart createCart(Long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        return repository.save(cart);
    }

    @Transactional
    public void deleteCartsByUserId(Long userId) {
        repository.deleteByUserId(userId);
    }

    @Transactional
    public void deleteCartsByCartId(Long cartId){
        repository.deleteById(cartId);
    }

    @Transactional
    public CartItem createItem(Long cartId, CartItemRequest request) {

        Cart cart = repository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart Not found with id : " + cartId));

        String url = PRODUCT_SERVICE_URL + request.productId();
        try {
            ResponseEntity<ApiResponse<Product>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<ApiResponse<Product>>() {});

            ApiResponse<Product> apiResponse = responseEntity.getBody();

            if(apiResponse != null && (apiResponse.getStatus() == HttpStatus.NOT_FOUND.value())){
                throw new ProductNotFoundException("Product not found with id : " + request.productId());
            }

            Product product = apiResponse.getData();

            if(request.quantity() > product.getAvailableQuantity()){
                throw new IllegalArgumentException("Requested quantity more than the available quantity");
            }

            CartItemId cartItemId = new CartItemId(cartId, product.getProductId());
            CartItem cartItem = new CartItem(cartItemId, cart, product.getProductId(), request.quantity());
            return cartItemRepository.save(cartItem);
        } catch (Exception e) {
            throw new RuntimeException("Error while retrieving product: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void deleteCartItem(Long cartId, Long productId) {
        Cart cart = repository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with card id : " + cartId));

        if(!cartItemRepository.existsByProductId(productId)){
            throw new IllegalArgumentException("No items in the cart with product id: " + productId);
        }

        CartItemId cartItemId = new CartItemId(cartId,productId);
        cartItemRepository.deleteById(cartItemId);
    }
}
