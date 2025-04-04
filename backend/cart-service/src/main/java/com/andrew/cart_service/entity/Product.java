package com.andrew.cart_service.entity;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Product {
    private Long productId;
    private String name;
    private Double price;
    private String image;
    private Long availableQuantity;
    private LocalDateTime createdAt;
}
