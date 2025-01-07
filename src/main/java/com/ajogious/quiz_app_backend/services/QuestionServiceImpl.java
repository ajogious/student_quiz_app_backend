package com.ajogious.quiz_app_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ajogious.quiz_app_backend.exceptions.QuestionNotFoundException;
import com.ajogious.quiz_app_backend.models.Question;
import com.ajogious.quiz_app_backend.repositories.QuestionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements IQuestionService {

	private final QuestionRepository questionRepository;

	@Override
	public Question saveQuestion(Question question) {
		return questionRepository.save(question);
	}

	@Override
	public List<Question> getAllQuestions() {
		return questionRepository.findAll();
	}

	@Override
	public Optional<Question> getQuestionById(Long id) {
		return questionRepository.findById(id);
	}

	@Override
	public List<String> getAllSubjects() {
		return questionRepository.findDistinctSubjects();
	}

	@Override
	public Optional<Question> updateAQuestion(Long id, Question updateQuestion) {
		return Optional.of(questionRepository.findById(id).map(existingQuestion -> {
			existingQuestion.setQuestion(updateQuestion.getQuestion());
			existingQuestion.setChoices(updateQuestion.getChoices());
			existingQuestion.setCorrectAnswers(updateQuestion.getCorrectAnswers());
			return questionRepository.save(existingQuestion);
		}).orElseThrow(() -> new QuestionNotFoundException("Question not found with id: " + id)));
	}

	@Override
	public void deleteAQuestion(Long id) {
		if (!questionRepository.existsById(id)) {
			throw new QuestionNotFoundException("Question not found.");
		}
		questionRepository.deleteById(id);
	}

	@Override
	public List<Question> getQuestionsForUser(Integer numOfQuestiosn, String subject) {
		Pageable pageable = PageRequest.of(0, numOfQuestiosn);
		return questionRepository.findBySubject(subject, pageable).getContent();
	}

}
