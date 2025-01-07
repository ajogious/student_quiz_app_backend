package com.ajogious.quiz_app_backend.services;

import java.util.*;

import com.ajogious.quiz_app_backend.models.Question;

public interface IQuestionService {
	
//	method to save a question
	Question saveQuestion(Question question);
	
//	method to get all questions
	List<Question> getAllQuestions();
	
//	method to get a question
	Optional<Question> getQuestionById(Long id);
	
//	method to get all available subject
	List<String> getAllSubjects();
	
//	method to update a question
	Optional<Question> updateAQuestion(Long id, Question updatedQuestion);

//	method to delete a question
	void deleteAQuestion(Long id);
	
//	method to to get questions for user
	List<Question> getQuestionsForUser(Integer numOfQuestiosn, String subject);

}
