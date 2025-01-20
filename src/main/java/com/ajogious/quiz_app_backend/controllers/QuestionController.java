package com.ajogious.quiz_app_backend.controllers;

import com.ajogious.quiz_app_backend.dtos.QuestionRequestDTO;
import com.ajogious.quiz_app_backend.dtos.QuestionResponseDTO;
import com.ajogious.quiz_app_backend.services.QuestionService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/questions")
public class QuestionController {

	private final QuestionService questionService;

	// End point for adding a question to a test
	@PostMapping("/{testId}/add-question")
	public ResponseEntity<String> addQuestion(@PathVariable Long testId,
			@RequestBody QuestionRequestDTO questionRequestDTO) {
		try {
			String response = questionService.addQuestion(testId, questionRequestDTO);
			if (response.startsWith("Question added successfully.")) {
				return ResponseEntity.ok(response);
			} else {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			}
		} catch (Exception e) { 
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	// End point on getting questions based on it's test
	@GetMapping("/{testId}/questions")
	public ResponseEntity<?> getQuestionsByTest(
	    @PathVariable Long testId,
	    @RequestParam(defaultValue = "0") int page,
	    @RequestParam(defaultValue = "10") int size,
	    @RequestParam(defaultValue = "") String search
	) {
	    try {
	        Page<QuestionResponseDTO> questions = questionService.getQuestionsByTest(testId, page, size, search);
	        return ResponseEntity.ok(questions);
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
	    }
	}
	
	@GetMapping("/{testId}/shuffled-questions")
	public ResponseEntity<List<QuestionResponseDTO>> getShuffledQuestions(@PathVariable Long testId) {
	    List<QuestionResponseDTO> shuffledQuestions = questionService.getShuffledQuestionsByTest(testId);
	    return ResponseEntity.ok(shuffledQuestions);
	}


	// End point on updating a test question
	@PutMapping("/{questionId}/update") 
	public ResponseEntity<String> updateQuestion(@PathVariable Long questionId,
			@RequestBody QuestionRequestDTO questionRequestDTO) {
		try {
			String response = questionService.updateQuestion(questionId, questionRequestDTO);
			if (response.startsWith("Question updated successfully.")) {
				return ResponseEntity.ok(response);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	// End point on deleting a test question
	@DeleteMapping("/{questionId}/delete")
	public ResponseEntity<String> deleteQuestion(@PathVariable Long questionId) {
		String response = questionService.deleteQuestion(questionId);
		try {
			if (response.startsWith("Question deleted successfully.")) {
				return ResponseEntity.ok(response);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong," + e.getMessage());
		}
	}
}
