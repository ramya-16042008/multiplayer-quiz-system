
package com.cipherx.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cipherx.model.Quiz;
import com.cipherx.repository.QuestionRepository;
import com.cipherx.repository.QuizRepository;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    public QuizService(
            QuizRepository quizRepository,
            QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Quiz> getQuizById(int quizId) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);

        quizOptional.ifPresent(quiz -> {
            quiz.setQuestions(
                questionRepository.findByQuizId(quizId)
            );
        });

        return quizOptional;
    }

    public Optional<Quiz> getQuizByCode(String quizCode) {
        return quizRepository.findByQuizCode(quizCode);
    }

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public void deleteQuiz(int quizId) {
        quizRepository.deleteById(quizId);
    }

    public boolean quizExists(int quizId) {
        return quizRepository.existsById(quizId);
    }
}