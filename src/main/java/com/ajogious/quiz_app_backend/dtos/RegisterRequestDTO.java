package com.ajogious.quiz_app_backend.dtos;

import lombok.*;

@Getter
@Setter
public class RegisterRequestDTO {
    private String fullName;
    private String email;
    private String password;
    private String confirmPassword;
}