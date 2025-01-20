package com.ajogious.quiz_app_backend.dtos;

import java.time.LocalDateTime;

import com.ajogious.quiz_app_backend.models.Result;

import lombok.Data;

@Data
public class ResultResponseDTO {
	private Long resultId;
	private String fullName;
	private String email;
	private String subject;
	private int totalQuestions;
	private int correctAnswers;
	private double percentage;
	private LocalDateTime testTakenAt;

	public ResultResponseDTO(Result result) {
		this.resultId = result.getId();
		this.fullName = result.getUser().getFullName();
		this.email = result.getUser().getEmail();
		this.subject = result.getTest().getSubject();
		this.totalQuestions = result.getTotalQuestions();
		this.correctAnswers = result.getCorrectAnswers();
		this.percentage = result.getPercentage();
		this.testTakenAt = result.getTestTakenAt();
	}

}
  