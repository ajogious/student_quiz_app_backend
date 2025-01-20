package com.ajogious.quiz_app_backend.dtos;

import lombok.*;

@Getter
@Setter
public class LoginRequestDTO {
    private String email;
    private String password;
}
