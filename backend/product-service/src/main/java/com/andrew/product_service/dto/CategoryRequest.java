package com.andrew.product_service.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank(message = "Category name must not be blank!")
        String name
) {
}
