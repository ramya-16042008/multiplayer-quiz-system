package com.cipherx.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cipherx.model.Quiz;
import com.cipherx.repository.QuizRepository;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin(origins = "*")
public class QuizController {

    private final QuizRepository quizRepository;

    public QuizController(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<Quiz> getQuiz(
            @PathVariable int quizId) {

        return quizRepository
                .findById(quizId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{quizCode}")
    public ResponseEntity<Quiz> getQuizByCode(
            @PathVariable String quizCode) {

        return quizRepository
                .findByQuizCode(quizCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(
            @RequestBody Quiz quiz) {

        quiz.setActive(false);

        Quiz savedQuiz =
                quizRepository.save(quiz);

        return ResponseEntity.ok(savedQuiz);
    }

    // ==============================
    // START QUIZ
    // ==============================

    @PatchMapping("/{quizId}/start")
    public ResponseEntity<?> startQuiz(
            @PathVariable int quizId) {

        return quizRepository
                .findById(quizId)
                .map(quiz -> {

                    quiz.startQuiz();

                    Quiz updatedQuiz =
                            quizRepository.save(quiz);

                    return ResponseEntity.ok(updatedQuiz);
                })
                .orElse(
                        ResponseEntity
                                .notFound()
                                .build()
                );
    }

    // ==============================
    // STOP QUIZ
    // ==============================

    @PatchMapping("/{quizId}/stop")
    public ResponseEntity<?> stopQuiz(
            @PathVariable int quizId) {

        return quizRepository
                .findById(quizId)
                .map(quiz -> {

                    quiz.stopQuiz();

                    Quiz updatedQuiz =
                            quizRepository.save(quiz);

                    return ResponseEntity.ok(updatedQuiz);
                })
                .orElse(
                        ResponseEntity
                                .notFound()
                                .build()
                );
    }

    // ==============================
    // DELETE QUIZ
    // ==============================

    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteQuiz(
            @PathVariable int quizId) {

        if (!quizRepository.existsById(quizId)) {
            return ResponseEntity.notFound().build();
        }

        quizRepository.deleteById(quizId);

        return ResponseEntity.noContent().build();
    }
}