package com.ajogious.quiz_app_backend.dtos;

import lombok.*;

@Getter
@Setter
public class TestRequestDTO {
    private String subject;
    private int timePerQuestion;
    private int markPerQuestion;
    private String description;
} 