package com.ajogious.quiz_app_backend.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
public class Result {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int totalQuestions;
	private int correctAnswers;
	private double percentage;
	private LocalDateTime testTakenAt;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "test_id")
	private Test test;
}
