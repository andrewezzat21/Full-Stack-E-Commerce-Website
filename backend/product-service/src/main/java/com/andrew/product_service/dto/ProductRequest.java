package com.andrew.product_service.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Set;

public record ProductRequest(
        @NotBlank(message = "Name must not be blank")
        String name,

        @NotNull(message = "Price must not be null")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        Double price,

        @NotBlank(message = "Image URL must not be blank")
        String image,

        @NotNull(message = "Available quantity must not be null")
        @PositiveOrZero(message = "Available quantity must be 0 or greater")
        Long availableQuantity,

        @NotEmpty(message = "At least one category must be selected")
        Set<Long> categoryIds
) {
}
