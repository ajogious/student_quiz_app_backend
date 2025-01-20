package com.ajogious.quiz_app_backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ajogious.quiz_app_backend.models.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

	Optional<Test> findBySubject(String subject);

}
