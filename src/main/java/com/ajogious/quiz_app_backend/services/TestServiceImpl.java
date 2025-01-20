package com.ajogious.quiz_app_backend.services;

import java.util.*;

import org.springframework.stereotype.Service;

import com.ajogious.quiz_app_backend.dtos.TestRequestDTO;
import com.ajogious.quiz_app_backend.dtos.TestResponseDTO;
import com.ajogious.quiz_app_backend.models.Test;
import com.ajogious.quiz_app_backend.repositories.TestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

	private final TestRepository testRepository;

	// Service implementation method for creating a test
	@Override 
	public String createTest(TestRequestDTO testRequestDTO) {
		try {
			Optional<Test> existingTest = testRepository.findBySubject(testRequestDTO.getSubject());
			if (existingTest.isPresent()) {
				return "Test with subject '" + testRequestDTO.getSubject() + "' already exists!";
			}

			Test test = new Test();
			test.setSubject(testRequestDTO.getSubject());
			test.setTimePerQuestion(testRequestDTO.getTimePerQuestion());
			test.setMarkPerQuestion(testRequestDTO.getMarkPerQuestion());
			test.setDescription(testRequestDTO.getDescription());

			testRepository.save(test); 
			return "Test created successfully.";
		} catch (Exception e) {
			throw new RuntimeException("Failed to create test! Try again.");
		}
	}

	// Service implementation method to get all tests
	@Override
	public List<TestResponseDTO> getAllTests() {
		try {
			List<Test> tests = testRepository.findAll();

			List<TestResponseDTO> testResponseDTOs = new ArrayList<>();
			for (Test test : tests) {
				testResponseDTOs.add(new TestResponseDTO(test));
			}

			return testResponseDTOs;
		} catch (Exception e) {
			throw new RuntimeException("Failed to fetch tests! Try again."); 
		}
	}

	// Service implementation method to get a test by it's id
	@Override
	public TestResponseDTO getTestById(Long id) {
		try {
			Test test = testRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Test with ID " + id + " not found."));

			return new TestResponseDTO(test);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException("Failed to fetch test! Try again.");
		}
	}

	// Service implementation method to update a test
	@Override
	public String updateTest(Long id, TestRequestDTO testRequestDTO) {
		try {
			Test test = testRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Test not found."));

			test.setSubject(testRequestDTO.getSubject());
			test.setTimePerQuestion(testRequestDTO.getTimePerQuestion());
			test.setMarkPerQuestion(testRequestDTO.getMarkPerQuestion());
			test.setDescription(testRequestDTO.getDescription());

			testRepository.save(test);
			return "Test updated successfully.";
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		} catch (Exception e) {
			return "Failed to update test! Try again.";
		}
	}

	// Service implementation method to delete a test
	@Override
	public String deleteTest(Long id) {
		try {
			if (!testRepository.existsById(id)) {
				throw new IllegalArgumentException("Test not found.");
			}

			testRepository.deleteById(id);
			return "Test deleted successfully.";
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		} catch (Exception e) {
			return "Failed to delete test! Try again.";
		}
	}

}