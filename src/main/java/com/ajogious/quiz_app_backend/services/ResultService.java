package com.ajogious.quiz_app_backend.services;

import java.util.List;

import com.ajogious.quiz_app_backend.dtos.ResultRequestDTO;
import com.ajogious.quiz_app_backend.dtos.ResultResponseDTO;

public interface ResultService {
	String evaluateTest(ResultRequestDTO resultRequestDTO);

	List<ResultResponseDTO> getUserResults(Long userId);

	List<ResultResponseDTO> getResults();

	void deleteResultById(Long resultId);
}
