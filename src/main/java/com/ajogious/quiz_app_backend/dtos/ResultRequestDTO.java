package com.ajogious.quiz_app_backend.dtos;

import lombok.Data;
import java.util.List;

@Data
public class ResultRequestDTO {
    private Long testId;
    private Long userId;
    private List<String> userAnswers;
}
   