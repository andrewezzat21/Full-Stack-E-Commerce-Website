package com.andrew.product_service.exception;

import com.andrew.product_service.dto.ApiResponse;
import com.andrew.product_service.entity.Category;
import com.andrew.product_service.entity.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiResponse<Category>> handleCategoryNotFound(CategoryNotFoundException exception){
        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(exception.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), null));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse<Product>> handleProductNotFound(ProductNotFoundException exception){
        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(exception.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Category>> handleValidationExceptions(MethodArgumentNotValidException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(exception.getBindingResult().getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Category>> handleAllExceptions(Exception exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(exception.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),null));
    }}
