package com.ajogious.quiz_app_backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ajogious.quiz_app_backend.models.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
//	List<Question> findByTestId(Long testId);

	@Query("SELECT q FROM Question q WHERE q.test.id = :testId")
	List<Question> findByTestId(@Param("testId") Long testId);

	Optional<Question> findByQuestionText(String questionText);

	Optional<Question> findByQuestionTextAndIdNot(String questionText, Long questionId);

	Optional<Question> findByQuestionTextAndTestId(String questionText, Long testId);
	
	@Query("SELECT q FROM Question q WHERE q.test.id = :testId AND " +   
		       "(LOWER(q.questionText) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		       "LOWER(q.correctAnswer) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		       "EXISTS (SELECT 1 FROM q.options opt WHERE LOWER(opt) LIKE LOWER(CONCAT('%', :search, '%'))))")
		Page<Question> findByTestIdWithSearch(@Param("testId") Long testId, @Param("search") String search, Pageable pageable);

}