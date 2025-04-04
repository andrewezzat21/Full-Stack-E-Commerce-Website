package com.andrew.cart_service.dto;

import jakarta.validation.constraints.*;

public record CartItemRequest(
        @NotBlank(message = "product id must not be null")
        String productId,

        @NotNull(message = "quantity must not be null")
        @Positive(message = "quantity must be 0 and less than available quantity")
        int quantity

) {

}
