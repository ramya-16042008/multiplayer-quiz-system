package com.cipherx.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cipherx.model.Question;
import com.cipherx.model.Quiz;
import com.cipherx.repository.ParticipantRepository;
import com.cipherx.repository.QuizRepository;
import com.cipherx.service.QuestionService;
import com.cipherx.service.QuizService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final QuizService quizService;
    private final QuestionService questionService;
    private final QuizRepository quizRepository;
    private final ParticipantRepository participantRepository;

    public AdminController(
            QuizService quizService,
            QuestionService questionService,
            QuizRepository quizRepository,
            ParticipantRepository participantRepository) {

        this.quizService = quizService;
        this.questionService = questionService;
        this.quizRepository = quizRepository;
        this.participantRepository = participantRepository;
    }


    // ==========================================
    // DASHBOARD STATISTICS
    // ==========================================

    @GetMapping("/statistics")
    public ResponseEntity<DashboardStatistics> getStatistics() {

        long totalQuizzes =
                quizRepository.countAllQuizzes();

        long activeQuizzes =
                quizRepository.countByActiveTrue();

        long totalParticipants =
                participantRepository.count();

        long completedQuizzes =
                quizRepository.countByCompletedTrue();

        DashboardStatistics statistics =
                new DashboardStatistics(
                        totalQuizzes,
                        activeQuizzes,
                        totalParticipants,
                        completedQuizzes
                );

        return ResponseEntity.ok(statistics);
    }


    // ==========================================
    // GET ALL QUIZZES
    // ==========================================

    @GetMapping("/quizzes")
    public ResponseEntity<List<QuizSummary>> getAllQuizzes() {

        List<QuizSummary> quizzes =
                quizService.getAllQuizzes()
                        .stream()
                        .map(quiz -> new QuizSummary(
                                quiz.getQuizId(),
                                quiz.getTitle(),
                                quiz.getDescription(),
                                quiz.getQuizCode(),
                                quiz.getTimePerQuestion(),
                                quiz.isActive(),
                                quiz.isCompleted()
                        ))
                        .collect(Collectors.toList());

        return ResponseEntity.ok(quizzes);
    }


    // ==========================================
    // CREATE QUIZ
    // ==========================================

    @PostMapping("/quizzes")
    public ResponseEntity<Quiz> createQuiz(
            @RequestBody Quiz quiz) {

        if (quiz == null) {
            return ResponseEntity.badRequest().build();
        }

        quiz.setActive(false);
        quiz.setCompleted(false);

        Quiz savedQuiz =
                quizService.createQuiz(quiz);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedQuiz);
    }


    // ==========================================
    // GET SINGLE QUIZ
    // ==========================================

    @GetMapping("/quizzes/{quizId}")
    public ResponseEntity<Quiz> getQuiz(
            @PathVariable int quizId) {

        return quizService.getQuizById(quizId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // ==========================================
    // DELETE QUIZ
    // ==========================================

    @DeleteMapping("/quizzes/{quizId}")
    public ResponseEntity<Void> deleteQuiz(
            @PathVariable int quizId) {

        if (!quizService.quizExists(quizId)) {
            return ResponseEntity.notFound().build();
        }

        quizService.deleteQuiz(quizId);

        return ResponseEntity.noContent().build();
    }


    // ==========================================
    // GET ALL QUESTIONS
    // ==========================================

    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getAllQuestions() {

        return ResponseEntity.ok(
                questionService.getAllQuestions()
        );
    }


    // ==========================================
    // ADD QUESTION
    // ==========================================

    @PostMapping("/questions")
    public ResponseEntity<Question> addQuestion(
            @RequestBody Question question) {

        if (question == null) {
            return ResponseEntity.badRequest().build();
        }

        Question savedQuestion =
                questionService.addQuestion(question);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedQuestion);
    }


    // ==========================================
    // GET SINGLE QUESTION
    // ==========================================

    @GetMapping("/questions/{questionId}")
    public ResponseEntity<Question> getQuestion(
            @PathVariable int questionId) {

        return questionService
                .getQuestionById(questionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // ==========================================
    // DELETE QUESTION
    // ==========================================

    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<Void> deleteQuestion(
            @PathVariable int questionId) {

        if (!questionService.questionExists(questionId)) {
            return ResponseEntity.notFound().build();
        }

        questionService.deleteQuestion(questionId);

        return ResponseEntity.noContent().build();
    }


    // ==========================================
    // DASHBOARD STATISTICS DTO
    // ==========================================

    public record DashboardStatistics(
            long totalQuizzes,
            long activeQuizzes,
            long totalParticipants,
            long completedQuizzes
    ) {}


    // ==========================================
    // QUIZ SUMMARY DTO
    // ==========================================

    public record QuizSummary(
            int quizId,
            String title,
            String description,
            String quizCode,
            int timePerQuestion,
            boolean active,
            boolean completed
    ) {}
}