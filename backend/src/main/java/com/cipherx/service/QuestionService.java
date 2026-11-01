package com.cipherx.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cipherx.model.Question;
import com.cipherx.repository.QuestionRepository;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Optional<Question> getQuestionById(int questionId) {
        return questionRepository.findById(questionId);
    }

    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    public void deleteQuestion(int questionId) {
        questionRepository.deleteById(questionId);
    }

    public boolean questionExists(int questionId) {
        return questionRepository.existsById(questionId);
    }
}