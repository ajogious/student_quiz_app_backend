package com.ajogious.quiz_app_backend.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserResponseDTO {
	private Long id;
    private String fullName;
    private String email;
    private LocalDateTime createdAt;
    private boolean isAdmin;
}