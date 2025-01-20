package com.ajogious.quiz_app_backend.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ajogious.quiz_app_backend.dtos.QuestionRequestDTO;
import com.ajogious.quiz_app_backend.dtos.QuestionResponseDTO;

public interface QuestionService {
	String addQuestion(Long testId, QuestionRequestDTO questionRequestDTO);

//	List<QuestionResponseDTO> getQuestionsByTest(Long testId);

	String updateQuestion(Long questionId, QuestionRequestDTO questionRequestDTO);

	String deleteQuestion(Long questionId);

	Page<QuestionResponseDTO> getQuestionsByTest(Long testId, int page, int size, String search);

	List<QuestionResponseDTO> getShuffledQuestionsByTest(Long testId);
}
