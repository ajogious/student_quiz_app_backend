package com.ajogious.quiz_app_backend.dtos;

import com.ajogious.quiz_app_backend.models.Test;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class TestResponseDTO {

	private Long id;
	private String subject;
	private String description;
	private int markPerQuestion; 
	private int timePerQuestion;
	private String formattedTotalTime; 
 
	public TestResponseDTO(Test test) {
		this.id = test.getId();
		this.subject = test.getSubject();
		this.description = test.getDescription();
		this.markPerQuestion = test.getMarkPerQuestion();
		this.timePerQuestion = test.getTimePerQuestion();
		this.formattedTotalTime = test.getFormattedTotalTime();
	}
}
