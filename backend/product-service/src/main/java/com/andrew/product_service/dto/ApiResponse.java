package com.andrew.product_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApiResponse<T>{
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private T data;
}
