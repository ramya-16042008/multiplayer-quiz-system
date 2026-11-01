package com.cipherx.security;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class SessionManager {

    private final Map<String, String> sessions =
            new ConcurrentHashMap<>();

    public String createSession(
            String username,
            String role) {

        String token = UUID.randomUUID().toString();

        sessions.put(
                token,
                username + ":" + role
        );

        return token;
    }

    public boolean isValidSession(String token) {
        return token != null
                && sessions.containsKey(token);
    }

    public String getSessionData(String token) {
        return sessions.get(token);
    }

    public void removeSession(String token) {
        if (token != null) {
            sessions.remove(token);
        }
    }
}