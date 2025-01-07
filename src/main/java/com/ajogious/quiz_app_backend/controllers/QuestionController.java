package com.ajogious.quiz_app_backend.controllers;

import java.util.*;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.ajogious.quiz_app_backend.dtos.ApiResponse;
import com.ajogious.quiz_app_backend.exceptions.QuestionNotFoundException;
import com.ajogious.quiz_app_backend.models.Question;
import com.ajogious.quiz_app_backend.services.IQuestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/quizzes")
public class QuestionController {

	private final IQuestionService questionService;

	@PostMapping("/create-question")
	public ResponseEntity<ApiResponse<Question>> saveQuestion(@Valid @RequestBody Question question) {
		Question savedQuestion = questionService.saveQuestion(question);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>("Question created successfully", "SUCCESS", savedQuestion));
	}

	@GetMapping("/all-questions")
	public ResponseEntity<ApiResponse<List<Question>>> getAllQuestion() {
		List<Question> questions = questionService.getAllQuestions();
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse<>("Fetched all questions", "SUCCESS", questions));
	}

	@GetMapping("/question/{id}")
	public ResponseEntity<ApiResponse<Optional<Question>>> getQuestionById(@PathVariable Long id) {
		Optional<Question> savedQuestion = questionService.getQuestionById(id);
		if (savedQuestion.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse<>("Question found", "SUCCESS", savedQuestion));
		} else {
			throw new QuestionNotFoundException("Question not found");
		}
	}

	@PutMapping("/question/{id}/update")
	public ResponseEntity<ApiResponse<Optional<Question>>> updateAQuestion(@PathVariable Long id,
			@RequestBody Question updateQuestion) {
		Optional<Question> updatedQuestion = questionService.updateAQuestion(id, updateQuestion);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse<>("Question updated successfully", "SUCCESS", updatedQuestion));
	}

	@DeleteMapping("/question/{id}/delete")
	public ResponseEntity<ApiResponse<Void>> deleteAQuestion(@PathVariable Long id) {
		questionService.deleteAQuestion(id);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse<>("Question deleted successfully", "SUCCESS", null));
	}

	@GetMapping("/get-all-subjects")
	public ResponseEntity<ApiResponse<List<String>>> getAllSubjects() {
		List<String> subjects = questionService.getAllSubjects();
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse<>("Subjects fetched successfully", "SUCCESS", subjects));
	}

	@GetMapping("/get-questions-for-user")
	public ResponseEntity<ApiResponse<Map<String, Object>>> getQuestionsForUser(
			@RequestParam(required = false) Integer numOfQuestions, @RequestParam String subject) {

		List<Question> allQuestions = questionService.getQuestionsForUser(numOfQuestions, subject);

		if (allQuestions.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ApiResponse<>("No questions found for the given subject", "ERROR", Collections.emptyMap()));
		}

		// If numOfQuestions is not provided, just return the available questions
		if (numOfQuestions == null) {
			Map<String, Object> response = new HashMap<>();
			response.put("totalQuestions", allQuestions.size());
			response.put("availableQuestions", allQuestions);
			return ResponseEntity
					.ok(new ApiResponse<>("Available questions fetched successfully", "SUCCESS", response));
		}

		// Shuffle and select the requested number of questions
		Collections.shuffle(allQuestions);
		int availableQuestions = Math.min(numOfQuestions, allQuestions.size());
		List<Question> selectedQuestions = allQuestions.subList(0, availableQuestions);

		Map<String, Object> response = new HashMap<>();
		response.put("selectedQuestions", selectedQuestions);
		response.put("totalQuestions", allQuestions.size());

		return ResponseEntity.ok(new ApiResponse<>("Questions fetched successfully", "SUCCESS", response));
	}

}
