package com.cipherx.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cipherx.model.Report;
import com.cipherx.repository.ReportRepository;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportRepository reportRepository;

    public ReportController(
            ReportRepository reportRepository) {

        this.reportRepository =
                reportRepository;
    }

    @GetMapping
    public List<Report> getAllReports() {

        return reportRepository.findAll();
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<Report>> getQuizReports(
            @PathVariable int quizId) {

        return ResponseEntity.ok(
                reportRepository.findByQuizId(quizId)
        );
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<Report> getReport(
            @PathVariable int reportId) {

        return reportRepository
                .findById(reportId)
                .map(ResponseEntity::ok)
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }
}