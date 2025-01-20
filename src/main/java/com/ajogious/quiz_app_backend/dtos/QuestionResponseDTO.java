package com.ajogious.quiz_app_backend.dtos;

import java.util.List;

import com.ajogious.quiz_app_backend.models.Question;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class QuestionResponseDTO {
	private Long id;
	private String questionText;
	private List<String> options;
	private String correctAnswer;

	public QuestionResponseDTO(Question question) {
		this.id = question.getId();
		this.questionText = question.getQuestionText();
		this.options = question.getOptions();
		this.correctAnswer = question.getCorrectAnswer();
	}
}