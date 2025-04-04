package com.andrew.cart_service.exception;

import com.andrew.cart_service.dto.ApiResponse;
import com.andrew.cart_service.entity.Product;
import jakarta.validation.constraints.Null;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse<Product>> handleProductNotFound(ProductNotFoundException exception){
        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(exception.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Null>> handleValidationExceptions(MethodArgumentNotValidException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(exception.getBindingResult().getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Null>> handleAllExceptions(Exception exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(exception.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),null));
    }
}
