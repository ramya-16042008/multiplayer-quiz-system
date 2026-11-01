package com.cipherx.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private int answerId;

    @Column(name = "quiz_id")
    private int quizId;

    @Column(name = "question_id", nullable = false)
    private int questionId;

    @Column(name = "player_id", nullable = false)
    private int playerId;

    @Column(name = "selected_option", nullable = false)
    private int selectedOption;

    @Column(name = "correct")
    private boolean correct;

    @Column(name = "response_time")
    private long responseTime;

    public Answer() {
    }

    public Answer(int answerId, int quizId, int questionId, int playerId,
                  int selectedOption, boolean correct,
                  long responseTime) {

        this.answerId = answerId;
        this.quizId = quizId;
        this.questionId = questionId;
        this.playerId = playerId;
        this.selectedOption = selectedOption;
        this.correct = correct;
        this.responseTime = responseTime;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(int selectedOption) {
        this.selectedOption = selectedOption;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }
}