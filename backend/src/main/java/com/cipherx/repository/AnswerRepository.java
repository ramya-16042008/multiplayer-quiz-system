package com.cipherx.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cipherx.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    List<Answer> findByQuizId(int quizId);

    List<Answer> findByQuestionId(int questionId);

    List<Answer> findByQuizIdAndPlayerId(int quizId, int playerId);

    boolean existsByQuizIdAndPlayerIdAndQuestionId(int quizId, int playerId, int questionId);
}
