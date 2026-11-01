package com.cipherx.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class LeaderboardWebSocket {

    @MessageMapping("/leaderboard/update")
    @SendTo("/topic/leaderboard")
    public String updateLeaderboard(String message) {
        return message;
    }
}