package com.cipherx.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cipherx.model.Player;
import com.cipherx.model.Quiz;
import com.cipherx.repository.ParticipantRepository;
import com.cipherx.repository.QuizRepository;

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = "*")
public class PlayerController {

    private final ParticipantRepository participantRepository;
    private final QuizRepository quizRepository;

    public PlayerController(
            ParticipantRepository participantRepository,
            QuizRepository quizRepository) {

        this.participantRepository =
                participantRepository;

        this.quizRepository =
                quizRepository;
    }

    @PostMapping("/join/{quizCode}")
    public ResponseEntity<?> joinQuiz(
            @PathVariable String quizCode,
            @RequestBody JoinRequest request) {

        Quiz quiz =
                quizRepository
                        .findByQuizCode(quizCode)
                        .orElse(null);

        if (quiz == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Quiz not found");
        }

        if (!quiz.getEntryPassword()
                .equals(request.entryPassword())) {

            return ResponseEntity
                    .status(401)
                    .body("Invalid entry password");
        }

        if (participantRepository
                .existsByUsername(request.playerName())) {

            return ResponseEntity
                    .badRequest()
                    .body("Player name already exists");
        }

        Player player =
                new Player(
                        0,
                        request.playerName(),
                        request.playerName()
                                + "@cipherx.local"
                );

        Player savedPlayer =
                participantRepository.save(player);

        return ResponseEntity.ok(savedPlayer);
    }

    public record JoinRequest(
            String playerName,
            String entryPassword
    ) {
    }
}