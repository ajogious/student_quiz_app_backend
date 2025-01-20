package com.ajogious.quiz_app_backend.services;

import com.ajogious.quiz_app_backend.dtos.LoginRequestDTO;
import com.ajogious.quiz_app_backend.dtos.RegisterRequestDTO;
import com.ajogious.quiz_app_backend.dtos.UserResponseDTO;

public interface UserService {
	String register(RegisterRequestDTO registerRequestDTO);
	String login(LoginRequestDTO loginRequestDTO);
	UserResponseDTO getUserDetails(String email);
	boolean logout(String token);
}
