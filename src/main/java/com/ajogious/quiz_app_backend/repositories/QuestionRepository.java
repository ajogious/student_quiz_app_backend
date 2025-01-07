package com.ajogious.quiz_app_backend.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ajogious.quiz_app_backend.models.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	@Query("SELECT DISTINCT q.subject FROM Question q")
	List<String> findDistinctSubjects();

	Page<Question> findBySubject(String subject, Pageable pageable);
}
