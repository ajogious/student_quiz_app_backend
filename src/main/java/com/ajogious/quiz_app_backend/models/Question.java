package com.ajogious.quiz_app_backend.models;

import java.util.*;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;

    @ElementCollection
    private List<String> options;

    private String correctAnswer;
      
    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;
}
