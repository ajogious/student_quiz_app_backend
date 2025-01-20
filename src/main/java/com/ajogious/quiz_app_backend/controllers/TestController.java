package com.ajogious.quiz_app_backend.controllers;

import java.util.List;

import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ajogious.quiz_app_backend.dtos.TestRequestDTO;
import com.ajogious.quiz_app_backend.dtos.TestResponseDTO;
import com.ajogious.quiz_app_backend.services.TestService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tests")   
public class TestController {

	private final TestService testService;

	// Creating a test end point
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/create-test")
	public ResponseEntity<String> createTest(@RequestBody TestRequestDTO testRequestDTO) {
		try {
			String response = testService.createTest(testRequestDTO);
			if (response.equals("Test created successfully.")) {
				return ResponseEntity.ok(response);
			} else {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	// Getting all method test end point
	@GetMapping
	public ResponseEntity<?> getAllTests() {
		try {
			List<TestResponseDTO> tests = testService.getAllTests();
			if (tests.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Available test.");
			}
			return ResponseEntity.ok(tests);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while fetching tests: " + e.getMessage());
		}
	}

	// Getting a test end point
	@GetMapping("/{id}")
	public ResponseEntity<?> getTestById(@PathVariable Long id) {
		try {
			TestResponseDTO test = testService.getTestById(id);
			return ResponseEntity.ok(test);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// Updating an existing test end point
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/{id}/update-test")
	public ResponseEntity<String> updateTest(@PathVariable Long id, @RequestBody TestRequestDTO testRequestDTO) {
		String response = testService.updateTest(id, testRequestDTO);
		if (response.startsWith("Test updated successfully")) {
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

	// Deleting an existing test end point   
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/{id}/delete-test")
	public ResponseEntity<String> deleteTest(@PathVariable Long id) {
		String response = testService.deleteTest(id);
		if (response.startsWith("Test deleted successfully")) {
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

}
