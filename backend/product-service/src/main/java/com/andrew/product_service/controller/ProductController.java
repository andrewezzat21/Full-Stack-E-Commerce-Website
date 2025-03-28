package com.andrew.product_service.controller;

import com.andrew.product_service.dto.ApiResponse;
import com.andrew.product_service.dto.CategoryRequest;
import com.andrew.product_service.dto.ProductRequest;
import com.andrew.product_service.entity.Category;
import com.andrew.product_service.entity.Product;
import com.andrew.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts(){
        List<Product> products = service.getAllProducts();
        return ResponseEntity.ok(new ApiResponse<>("All Products",
                HttpStatus.OK.value(),
                LocalDateTime.now(),
                products));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long productId){
        Product product = service.getProductById(productId);
        return ResponseEntity.ok(new ApiResponse<>("Product Found",
                HttpStatus.OK.value(),
                LocalDateTime.now(),
                product));
    }

    @GetMapping("/{productId}/categories")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategoriesByProduct(@PathVariable Long productId){
        List<Category> categories = service.getAllCategoriesByProduct(productId);
        return ResponseEntity.ok(new ApiResponse<>("All Categories of product id: " + productId,
                HttpStatus.OK.value(),
                LocalDateTime.now(),
                categories));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Product>> createCategory(@RequestBody @Valid ProductRequest request){
        Product product  = service.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Product created successfully!",
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now(),
                        product));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<Product>> updateProductById(
            @PathVariable Long productId,
            @RequestBody @Valid ProductRequest request){
        Product product = service.updateProductById(productId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Product Updated successfully!",
                        HttpStatus.OK.value(),
                        LocalDateTime.now(),
                        product));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Product>> deleteProductById(@PathVariable Long productId){
        service.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Product Deleted successfully!",
                        HttpStatus.OK.value(),
                        LocalDateTime.now(),
                        null));
    }

}
