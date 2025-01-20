package com.ajogious.quiz_app_backend.services;

import java.util.List;

import com.ajogious.quiz_app_backend.dtos.TestRequestDTO;
import com.ajogious.quiz_app_backend.dtos.TestResponseDTO;

public interface TestService {
    String createTest(TestRequestDTO testRequestDTO);
    List<TestResponseDTO> getAllTests();
    TestResponseDTO getTestById(Long id);
    String updateTest(Long id, TestRequestDTO testRequestDTO);
    String deleteTest(Long id);
} 