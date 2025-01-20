package com.ajogious.quiz_app_backend.securities;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.ajogious.quiz_app_backend.models.User;
import com.ajogious.quiz_app_backend.repositories.UserRepository;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with this email: " + email);
		}
		return new UserPrinciple(user);
	}
}
