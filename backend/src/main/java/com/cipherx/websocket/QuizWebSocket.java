package com.cipherx.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class QuizWebSocket {

    @MessageMapping("/quiz/join")
    @SendTo("/topic/quiz")
    public String joinQuiz(String message) {
        return message;
    }

    @MessageMapping("/quiz/answer")
    @SendTo("/topic/quiz")
    public String submitAnswer(String message) {
        return message;
    }

    @MessageMapping("/quiz/status")
    @SendTo("/topic/quiz")
    public String quizStatus(String message) {
        return message;
    }
}