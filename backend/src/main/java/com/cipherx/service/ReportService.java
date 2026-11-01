package com.cipherx.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cipherx.model.Report;
import com.cipherx.repository.ReportRepository;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(
            ReportRepository reportRepository) {

        this.reportRepository =
                reportRepository;
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Optional<Report> getReportById(int reportId) {
        return reportRepository.findById(reportId);
    }

    public List<Report> getReportsByQuiz(int quizId) {
        return reportRepository.findByQuizId(quizId);
    }

    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }
}