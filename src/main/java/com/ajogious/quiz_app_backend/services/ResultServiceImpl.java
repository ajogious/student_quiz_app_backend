package com.ajogious.quiz_app_backend.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ajogious.quiz_app_backend.dtos.ResultRequestDTO;
import com.ajogious.quiz_app_backend.dtos.ResultResponseDTO;
import com.ajogious.quiz_app_backend.models.Question;
import com.ajogious.quiz_app_backend.models.Result;
import com.ajogious.quiz_app_backend.models.Test;
import com.ajogious.quiz_app_backend.models.User;
import com.ajogious.quiz_app_backend.repositories.ResultRepository;
import com.ajogious.quiz_app_backend.repositories.TestRepository;
import com.ajogious.quiz_app_backend.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {

	private final ResultRepository resultRepository;
	private final UserRepository userRepository;
	private final TestRepository testRepository;

	@Override
	public String evaluateTest(ResultRequestDTO resultRequestDTO) {
		
		User user = userRepository.findById(resultRequestDTO.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));
		Test test = testRepository.findById(resultRequestDTO.getTestId())
				.orElseThrow(() -> new RuntimeException("Test not found"));

		List<String> userAnswers = resultRequestDTO.getUserAnswers();

		// Get correct answers from the test
		List<String> correctAnswers = new ArrayList<>();
		if (test.getQuestions() != null) {
			for (Question q : test.getQuestions()) {
				correctAnswers.add(q.getCorrectAnswer());
			}
		} else {
			throw new RuntimeException("Questions list is null.");
		}

		int totalQuestions = correctAnswers.size();
		int correctAnswerCount = 0;

		// Compare user answers with correct answers, handling missing user answers as
		// incorrect
		for (int i = 0; i < totalQuestions; i++) {
			if (i < userAnswers.size() && correctAnswers.get(i).equals(userAnswers.get(i))) {
				correctAnswerCount++;
			}
		}

		double percentage = ((double) correctAnswerCount / totalQuestions) * 100;

		Result result = new Result();
		result.setUser(user);
		result.setTest(test);
		result.setTotalQuestions(totalQuestions);
		result.setCorrectAnswers(correctAnswerCount);
		result.setPercentage(percentage);
		result.setTestTakenAt(LocalDateTime.now());
  
		resultRepository.save(result);
		return "Test submitted successfully.";
	}

	@Override
	public List<ResultResponseDTO> getUserResults(Long userId) { 
		List<Result> results = resultRepository.findByUserId(userId);    
 
		List<ResultResponseDTO> responseDTOs = new ArrayList<>();
		for (Result result : results) {          
			responseDTOs.add(new ResultResponseDTO(result));
		}    
		return responseDTOs;
	}

	@Override
	public List<ResultResponseDTO> getResults() {
		List<Result> results = resultRepository.findAll();
		List<ResultResponseDTO> responseDTOs = new ArrayList<>();
		for (Result result : results) {          
			responseDTOs.add(new ResultResponseDTO(result));
		}    
		return responseDTOs;
	}

	@Override
	public void deleteResultById(Long resultId) {
		if (!resultRepository.existsById(resultId)) {
            throw new RuntimeException("Result does not exist.");
        }
        resultRepository.deleteById(resultId);
	}    
    
}