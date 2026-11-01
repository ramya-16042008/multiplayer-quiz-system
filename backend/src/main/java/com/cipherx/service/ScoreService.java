package com.cipherx.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cipherx.model.Score;
import com.cipherx.repository.ScoreRepository;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;

    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    public Optional<Score> getScoreById(int scoreId) {
        return scoreRepository.findById(scoreId);
    }

    public List<Score> getScoresByQuiz(int quizId) {
        return scoreRepository.findByQuizId(quizId);
    }

    public List<Score> getScoresByPlayer(int playerId) {
        return scoreRepository.findByPlayerId(playerId);
    }

    public Score saveScore(Score score) {
        return scoreRepository.save(score);
    }
}