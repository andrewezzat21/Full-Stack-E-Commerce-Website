package com.andrew.user_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponse<T> {
    private String message;
    private int status;
    private LocalDateTime timeStamp;
    private T data;
}
