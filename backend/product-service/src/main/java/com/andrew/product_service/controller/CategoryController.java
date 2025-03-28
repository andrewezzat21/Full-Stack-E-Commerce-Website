package com.andrew.product_service.controller;

import com.andrew.product_service.dto.ApiResponse;
import com.andrew.product_service.dto.CategoryRequest;
import com.andrew.product_service.entity.Category;
import com.andrew.product_service.entity.Product;
import com.andrew.product_service.service.CategoryService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;


    @GetMapping()
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories(){
        List<Category> categories = service.getAllCategories();
        return ResponseEntity.ok(new ApiResponse<>("All Categories",
                HttpStatus.OK.value(),
                LocalDateTime.now(),
                categories));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable Long categoryId){
        Category category = service.getCategoryById(categoryId);
        return ResponseEntity.ok(new ApiResponse<>("Category Found",
                HttpStatus.OK.value(),
                LocalDateTime.now(),
                category));
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<ApiResponse<List<Product>>> getAllProductsByCategory(@PathVariable Long categoryId){
        List<Product> products = service.getAllProductsByCategory(categoryId);
        return ResponseEntity.ok(new ApiResponse<>("All Products of Category id: " + categoryId,
                HttpStatus.OK.value(),
                LocalDateTime.now(),
                products));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Category>> createCategory(@RequestBody @Valid CategoryRequest request){
        Category category = service.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Category created successfully!",
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now(),
                        category));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Category>> updateCategoryById(
            @PathVariable Long categoryId,
            @RequestBody @Valid CategoryRequest request){
        Category category = service.updateCategoryById(categoryId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Category Updated successfully!",
                        HttpStatus.OK.value(),
                        LocalDateTime.now(),
                        category));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Category>> deleteCategoryById(@PathVariable Long categoryId){
        service.deleteCategoryById(categoryId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Category Deleted successfully!",
                        HttpStatus.OK.value(),
                        LocalDateTime.now(),
                        null));
    }
}
