package com.cipherx.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "scores")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id")
    private int scoreId;

    @Column(name = "player_id", nullable = false)
    private int playerId;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "quiz_id", nullable = false)
    private int quizId;

    @Column(name = "total_score")
    private int totalScore;

    @Column(name = "correct_answers")
    private int correctAnswers;

    @Column(name = "wrong_answers")
    private int wrongAnswers;

    @Column(name = "total_response_time")
    private long totalResponseTime;

    public Score() {
    }

    public Score(int scoreId, int playerId, int quizId) {
        this.scoreId = scoreId;
        this.playerId = playerId;
        this.quizId = quizId;
        this.totalScore = 0;
        this.correctAnswers = 0;
        this.wrongAnswers = 0;
        this.totalResponseTime = 0;
    }

    public Score(int scoreId, int playerId, String playerName, int quizId) {
        this.scoreId = scoreId;
        this.playerId = playerId;
        this.playerName = playerName;
        this.quizId = quizId;
        this.totalScore = 0;
        this.correctAnswers = 0;
        this.wrongAnswers = 0;
        this.totalResponseTime = 0;
    }

    public int getScoreId() {
        return scoreId;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public long getTotalResponseTime() {
        return totalResponseTime;
    }

    public void setTotalResponseTime(long totalResponseTime) {
        this.totalResponseTime = totalResponseTime;
    }

    public void addCorrectAnswer(int points, long responseTime) {
        totalScore += points;
        correctAnswers++;
        totalResponseTime += responseTime;
    }

    public void addWrongAnswer(long responseTime) {
        wrongAnswers++;
        totalResponseTime += responseTime;
    }
}