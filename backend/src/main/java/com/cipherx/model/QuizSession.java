package com.cipherx.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "quiz_sessions")
public class QuizSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private int sessionId;

    @Column(name = "quiz_id", nullable = false)
    private int quizId;

    @Column(name = "current_question_index")
    private int currentQuestionIndex;

    @Column(name = "active")
    private boolean active;

    @Transient
    private Quiz quiz;

    @Transient
    private List<Player> players = new ArrayList<>();

    public QuizSession() {
    }

    public QuizSession(int sessionId, int quizId) {
        this.sessionId = sessionId;
        this.quizId = quizId;
        this.currentQuestionIndex = 0;
        this.active = false;
        this.players = new ArrayList<>();
    }

    public QuizSession(int sessionId, Quiz quiz) {
        this.sessionId = sessionId;
        this.quiz = quiz;
        if (quiz != null) {
            this.quizId = quiz.getQuizId();
        }
        this.players = new ArrayList<>();
        this.currentQuestionIndex = 0;
        this.active = false;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        if (quiz != null) {
            this.quizId = quiz.getQuizId();
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void addPlayer(Player player) {
        if (player != null && !players.contains(player)) {
            players.add(player);
        }
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void startSession() {
        this.active = true;
    }

    public void endSession() {
        this.active = false;
    }

    public void nextQuestion() {
        if (quiz != null && quiz.getQuestions() != null) {
            if (currentQuestionIndex < quiz.getQuestions().size() - 1) {
                currentQuestionIndex++;
            }
        } else {
            currentQuestionIndex++;
        }
    }
}