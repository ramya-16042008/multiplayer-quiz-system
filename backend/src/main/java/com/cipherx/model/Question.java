
package com.cipherx.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private int questionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", insertable = false, updatable = false)
    @JsonIgnore
    private Quiz quiz;

    @Column(name = "quiz_id")
    private Integer quizId;

    @Column(name = "question_text", nullable = false)
    private String questionText;

    @ElementCollection
    @CollectionTable(
        name = "question_options",
        joinColumns = @JoinColumn(name = "question_id")
    )
    @Column(name = "option_text", nullable = false)
    private List<String> options = new ArrayList<>();

    @Column(name = "correct_option")
    private int correctOption;

    @Column(name = "points")
    private int points;

    public Question() {
    }

    public Question(int questionId, String questionText,
                    List<String> options, int correctOption, int points) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.options = options != null
                ? new ArrayList<>(options) : new ArrayList<>();
        this.correctOption = correctOption;
        this.points = points;
    }

    public Question(int questionId, Integer quizId, String questionText,
                    List<String> options, int correctOption, int points) {
        this(questionId, questionText, options, correctOption, points);
        this.quizId = quizId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        this.quizId = quiz != null ? quiz.getQuizId() : null;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options != null
                ? new ArrayList<>(options) : new ArrayList<>();
    }

    public int getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(int correctOption) {
        this.correctOption = correctOption;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isCorrect(int selectedOption) {
        return selectedOption == correctOption;
    }
}