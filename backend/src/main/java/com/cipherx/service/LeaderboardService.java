package com.cipherx.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cipherx.model.Score;

@Service
public class LeaderboardService {

    private final ScoreService scoreService;

    public LeaderboardService(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    public List<Score> getLeaderboard(int quizId) {

        List<Score> scores = scoreService.getScoresByQuiz(quizId);

        scores.sort(
                Comparator.comparingInt(Score::getTotalScore)
                        .reversed()
                        .thenComparingLong(Score::getTotalResponseTime)
        );

        return scores;
    }
}