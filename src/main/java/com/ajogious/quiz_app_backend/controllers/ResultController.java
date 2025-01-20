package com.ajogious.quiz_app_backend.controllers;

import com.ajogious.quiz_app_backend.dtos.ResultRequestDTO;
import com.ajogious.quiz_app_backend.dtos.ResultResponseDTO;
import com.ajogious.quiz_app_backend.services.ResultService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/results") 
public class ResultController {

	private final ResultService resultService;

	@PostMapping("/submit-test") 
	public ResponseEntity<String> submitTest(@RequestBody ResultRequestDTO resultRequestDTO) {
		try {
			String response = resultService.evaluateTest(resultRequestDTO);
			
			if(response.startsWith("Test submitted successfully.")) {
				return ResponseEntity.ok(response);				
			} else {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			}
		} catch(RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/view-results")
	public ResponseEntity<?> getResults() {
		try {    
			List<ResultResponseDTO> results = resultService.getResults();
			return ResponseEntity.ok(results);
		} catch(RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/view-results/{userId}")
	public ResponseEntity<?> viewUserResults(@PathVariable Long userId) {
		try {    
			List<ResultResponseDTO> results = resultService.getUserResults(userId);
			return ResponseEntity.ok(results);
		} catch(RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	
	 @DeleteMapping("/{resultId}")
	    public ResponseEntity<String> deleteResult(@PathVariable Long resultId) {
	        try {
	            resultService.deleteResultById(resultId);
	            return ResponseEntity.ok("Result deleted successfully.");
	        } catch (RuntimeException e) {
	            return ResponseEntity.badRequest().body(e.getMessage());
	        }
	    }
	
	
}  