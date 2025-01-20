package com.ajogious.quiz_app_backend.dtos;

import java.util.*;

import lombok.*;

@Getter
@Setter
public class QuestionRequestDTO {
    private String questionText;
    private List<String> options;
    private String correctAnswer;
}