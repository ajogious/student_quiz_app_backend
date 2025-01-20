package com.ajogious.quiz_app_backend.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ajogious.quiz_app_backend.dtos.LoginRequestDTO;
import com.ajogious.quiz_app_backend.dtos.RegisterRequestDTO;
import com.ajogious.quiz_app_backend.dtos.UserResponseDTO;
import com.ajogious.quiz_app_backend.models.Role;
import com.ajogious.quiz_app_backend.models.User;
import com.ajogious.quiz_app_backend.repositories.UserRepository;
import com.ajogious.quiz_app_backend.securities.JWTUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final AuthenticationManager authenticationManager;

	private final JWTUtil jwtUtil;

	// Service implementation method to register user
	@Override
	public String register(RegisterRequestDTO registerRequestDTO) {
	    try {
	        User existingUser = userRepository.findByEmail(registerRequestDTO.getEmail()); // Checking for duplicate email before registering user
	        if (existingUser != null) {
	            return "Email already exists.";
	        }

	        if (!registerRequestDTO.getPassword().equals(registerRequestDTO.getConfirmPassword())) { // Checking to make sure password matched
	            return "Passwords do not match.";
	        }

	        // Saving the user if all above checks are okay
	        User newUser = new User();
	        newUser.setFullName(registerRequestDTO.getFullName());
	        newUser.setEmail(registerRequestDTO.getEmail());
	        newUser.setPassword(bCryptPasswordEncoder.encode(registerRequestDTO.getPassword()));

	        userRepository.save(newUser);

	        return "User registered successfully.";
	    } catch (Exception e) {
	    	 e.printStackTrace();
	        return "An error occurred while registering the user. Please try again later.";
	    }
	}
 
	// Service implementation for login user
	@Override
	public String login(LoginRequestDTO loginRequestDTO) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())); // For verifying user

			if (authentication.isAuthenticated()) {
				return jwtUtil.generateToken(loginRequestDTO.getEmail()); // Generating token for the user
			} else {
				throw new IllegalArgumentException("Authentication failed");
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Bad Credentials!");
		}
	}

	// Service implementation method to return user's details when login was successful
	@Override
	public UserResponseDTO getUserDetails(String email) {
		try {
			User user = userRepository.findByEmail(email);
			if (user == null) {
				throw new IllegalArgumentException("User not found.");
			}
			return new UserResponseDTO(user.getId(), user.getFullName(), user.getEmail(), user.getCreatedAt(),
					user.getRole() == Role.ADMIN);
		} catch (Exception e) {
			throw new IllegalArgumentException("Internal server error!, Try to login again.");
		} 
		
		
	}

	 private final Set<String> tokenBlacklist = new HashSet<>();
	 @Override
	 public boolean logout(String token) {
	     if (token == null || token.isEmpty()) {
	         return false;
	     }
	     if (jwtUtil.isTokenExpired(token)) {
	         return true; // Expired tokens don't need to be blacklisted.
	     }
	     tokenBlacklist.add(token); // Add valid token to the blacklist.
	     return true;
	 }


}
