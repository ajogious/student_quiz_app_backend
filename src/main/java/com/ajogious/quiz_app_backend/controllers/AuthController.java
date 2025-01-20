package com.ajogious.quiz_app_backend.controllers;

import java.util.*;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.ajogious.quiz_app_backend.dtos.LoginRequestDTO;
import com.ajogious.quiz_app_backend.dtos.RegisterRequestDTO;
import com.ajogious.quiz_app_backend.dtos.UserResponseDTO;
import com.ajogious.quiz_app_backend.services.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UserService userService;

	// Registering user's end point
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
		try {
			String response = userService.register(registerRequestDTO);
			if ("User registered successfully.".equals(response)) {
				return ResponseEntity.status(HttpStatus.CREATED).body(response);
			} else if ("Email already exists.".equals(response)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			} else if ("Passwords do not match.".equals(response)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			} 
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
		}
	}

	
	// Login user's end point
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestDTO loginRequestDTO) {
		Map<String, Object> response = new HashMap<>();
		try {
			String token = userService.login(loginRequestDTO);

			UserResponseDTO userDetails = userService.getUserDetails(loginRequestDTO.getEmail());

			response.put("message", "Login successful.");
			response.put("token", token);
			response.put("userDetails", userDetails);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (IllegalArgumentException e) {
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		} catch (Exception e) {
			response.put("message", "An unexpected error occurred");
			response.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// Logout user's end point
	@PostMapping("/logout")
	public ResponseEntity<Map<String, String>> logout(@RequestHeader("Authorization") String token) {
	    Map<String, String> response = new HashMap<>();
	    try {
	        if (token != null && token.startsWith("Bearer ")) {
	            token = token.substring(7);
	        } else {
	            response.put("message", "Invalid token format.");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }

	        boolean isLoggedOut = userService.logout(token);
	        if (isLoggedOut) {
	            response.put("message", "Logout successful.");
	            return ResponseEntity.status(HttpStatus.OK).body(response);
	        } else {
	            response.put("message", "Logout failed. Invalid or missing token.");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("message", "An unexpected error occurred.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}


}
