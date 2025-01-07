package com.ajogious.quiz_app_backend.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String message;
    private String status;
    private T data;
}