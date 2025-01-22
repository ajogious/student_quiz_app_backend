package com.ajogious.quiz_app_backend.services;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ajogious.quiz_app_backend.dtos.*;
import com.ajogious.quiz_app_backend.models.Question;
import com.ajogious.quiz_app_backend.models.Test;
import com.ajogious.quiz_app_backend.repositories.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

	private final QuestionRepository questionRepository;

	private final TestRepository testRepository;

	// Service implementation method to create a question
	@Override
	public String addQuestion(Long testId, QuestionRequestDTO questionRequestDTO) {
		try {
			Test test = testRepository.findById(testId).orElseThrow(() -> new RuntimeException("Test not found!"));

			Optional<Question> existingQuestion = questionRepository
					.findByQuestionTextAndTestId(questionRequestDTO.getQuestionText(), testId);

			if (existingQuestion.isPresent()) {
				return "Question already exists!";
			}

			Question question = new Question();
			question.setQuestionText(questionRequestDTO.getQuestionText());
			question.setOptions(questionRequestDTO.getOptions());
			question.setCorrectAnswer(questionRequestDTO.getCorrectAnswer());
			question.setTest(test);

			questionRepository.save(question);

			return "Question added successfully.";
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("Failed to save question! Try again.", e);
		}
	}

	// Service implementation method to get questions based on it's test
	@Override
	public Page<QuestionResponseDTO> getQuestionsByTest(Long testId, int page, int size, String search) {
	    Pageable pageable = PageRequest.of(page, size);

	    Page<Question> questions = questionRepository.findByTestIdWithSearch(testId, search, pageable);
	    
//	    Collections.shuffle(null);

	    if (questions.isEmpty()) {
	        throw new RuntimeException("No questions available.");
	    }

	    return questions.map(QuestionResponseDTO::new);
	}
	
	// Getting questions with shuffles
	@Override
	public List<QuestionResponseDTO> getShuffledQuestionsByTest(Long testId) {
	    // Fetch all questions for the given test ID
	    List<Question> questions = questionRepository.findByTestId(testId);

	    if (questions.isEmpty()) {
	        throw new RuntimeException("No questions available.");
	    }

	    // Shuffle the questions
	    Collections.shuffle(questions);

	    // Shuffle the options for each question
	    questions.forEach(question -> Collections.shuffle(question.getOptions()));

	    // Convert the shuffled questions to DTOs and return
	    return questions.stream().map(QuestionResponseDTO::new).toList();
	}



	// Service implementation method to update a question
	@Override
	public String updateQuestion(Long questionId, QuestionRequestDTO questionRequestDTO) {
		try {
			Question question = questionRepository.findById(questionId)
					.orElseThrow(() -> new IllegalArgumentException("Question not found"));

			question.setQuestionText(questionRequestDTO.getQuestionText());
			question.setOptions(questionRequestDTO.getOptions());
			question.setCorrectAnswer(questionRequestDTO.getCorrectAnswer());

			questionRepository.save(question);

			return "Question updated successfully.";
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		} catch (Exception e) {
			return "Failed to update question! Try again.";
		}
	}

	// Service implementation method to delete a question
	@Override
	public String deleteQuestion(Long questionId) {
		try {
			if (!questionRepository.existsById(questionId)) {
				throw new IllegalArgumentException("Question not found.");
			}
			questionRepository.deleteById(questionId);
			return "Question deleted successfully.";
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		} catch (Exception e) {
			return "Failed to delete question! Try again.";
		}
	}

}