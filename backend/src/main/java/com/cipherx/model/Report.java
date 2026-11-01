package com.cipherx.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private int reportId;

    @Column(name = "quiz_id", nullable = false)
    private int quizId;

    @Column(name = "quiz_title")
    private String quizTitle;

    @Column(name = "total_players")
    private int totalPlayers;

    @Column(name = "total_questions")
    private int totalQuestions;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Score> scores = new ArrayList<>();

    public Report() {
    }

    public Report(int reportId, int quizId, String quizTitle,
                  int totalPlayers, int totalQuestions,
                  List<Score> scores) {

        this.reportId = reportId;
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.totalPlayers = totalPlayers;
        this.totalQuestions = totalQuestions;
        this.scores = scores;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public int getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(int totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }
}