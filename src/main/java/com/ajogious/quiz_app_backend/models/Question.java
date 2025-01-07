package com.ajogious.quiz_app_backend.models;

import java.util.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String question;
	@NotBlank
	private String subject;
	@NotBlank
	private String questionType;
	
	@NotEmpty
	@ElementCollection
	private List<String> choices;
	
	@NotEmpty
	@ElementCollection
	private List<String> correctAnswers;
}
