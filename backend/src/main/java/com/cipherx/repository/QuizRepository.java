package com.cipherx.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cipherx.model.Quiz;

public interface QuizRepository
        extends JpaRepository<Quiz, Integer> {

    Optional<Quiz> findByQuizCode(String quizCode);

    long countByActiveTrue();

    long countByCompletedTrue();

    @Query("SELECT COUNT(q) FROM Quiz q")
    long countAllQuizzes();
}