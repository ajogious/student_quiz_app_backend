package com.ajogious.quiz_app_backend.models;

import java.time.Duration;
import java.util.*;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
public class Test {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String subject;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private int markPerQuestion;

	@Column(nullable = false)
	private int timePerQuestion;

	@OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
	private List<Question> questions;

	@OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
	private List<Result> results;

	public int calculateTotalTime() {
		int totalQuestions = questions.size();
		return totalQuestions * timePerQuestion;
	}
   
	public String getFormattedTotalTime() {
		int totalSeconds = calculateTotalTime();
		Duration duration = Duration.ofSeconds(totalSeconds);
		long hours = duration.toHours();  
		long minutes = duration.toMinutesPart();     
		long seconds = duration.toSecondsPart();
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}
}