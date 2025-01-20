package com.ajogious.quiz_app_backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ajogious.quiz_app_backend.models.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

	List<Result> findByUserId(Long userId);
	boolean existsByUserIdAndTestId(Long userId, Long testId);
}
