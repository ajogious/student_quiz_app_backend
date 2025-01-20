package com.ajogious.quiz_app_backend.models;

import java.time.LocalDateTime;
import java.util.*;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 

	@Column(nullable = false)
	private String fullName;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;   
	
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Result> results;

	@PrePersist
	void setUserRole() {
		if (this.role == null || createdAt == null) {
			this.role = Role.USER;
			this.createdAt = LocalDateTime.now();
		}
	} 
}
