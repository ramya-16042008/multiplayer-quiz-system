package com.cipherx.repository;

import com.cipherx.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Integer> {

    List<Score> findByQuizId(int quizId);

    List<Score> findByPlayerId(int playerId);

    Optional<Score> findByQuizIdAndPlayerId(int quizId, int playerId);
}