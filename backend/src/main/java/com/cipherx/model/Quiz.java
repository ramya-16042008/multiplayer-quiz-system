package com.cipherx.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private int quizId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "quiz_code", nullable = false, unique = true)
    private String quizCode;

    @Column(name = "entry_password", nullable = false)
    private String entryPassword;

    @Column(name = "time_per_question", nullable = false)
    private int timePerQuestion;

    @Column(name = "active")
    private boolean active = false;

    @Column(name = "completed")
    private boolean completed = false;

    @OneToMany(
        mappedBy = "quiz",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties("quiz")
    private List<Question> questions = new ArrayList<>();

    public Quiz() {
    }

    public Quiz(
            int quizId,
            String title,
            String description,
            String quizCode,
            String entryPassword,
            int timePerQuestion,
            boolean active,
            List<Question> questions) {

        this.quizId = quizId;
        this.title = title;
        this.description = description;
        this.quizCode = quizCode;
        this.entryPassword = entryPassword;
        this.timePerQuestion = timePerQuestion;
        this.active = active;
        this.completed = false;
        this.questions = questions != null
                ? questions
                : new ArrayList<>();
    }

    public int getQuizId() {
        return quizId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getQuizCode() {
        return quizCode;
    }

    public String getEntryPassword() {
        return entryPassword;
    }

    public int getTimePerQuestion() {
        return timePerQuestion;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isCompleted() {
        return completed;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuizCode(String quizCode) {
        this.quizCode = quizCode;
    }

    public void setEntryPassword(String entryPassword) {
        this.entryPassword = entryPassword;
    }

    public void setTimePerQuestion(int timePerQuestion) {
        this.timePerQuestion = timePerQuestion;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions != null
                ? questions
                : new ArrayList<>();
    }

    public void addQuestion(Question question) {
        if (question != null) {
            questions.add(question);
            question.setQuiz(this);
        }
    }

    public void startQuiz() {
        this.active = true;
        this.completed = false;
    }

    public void stopQuiz() {
        this.active = false;
        this.completed = true;
    }
}