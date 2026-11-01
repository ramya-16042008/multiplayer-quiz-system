package com.cipherx.repository;

import com.cipherx.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Integer> {

    List<Report> findByQuizId(int quizId);

    Optional<Report> findFirstByQuizId(int quizId);
}