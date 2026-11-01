package com.cipherx.controller;

import com.cipherx.security.SessionManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final SessionManager sessionManager;

    public AuthController(
            SessionManager sessionManager) {

        this.sessionManager =
                sessionManager;
    }


    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        if ("admin".equals(request.username())
                && "admin123".equals(request.password())) {

            String token =
                    sessionManager.createSession(
                            request.username(),
                            "ADMIN"
                    );

            return new LoginResponse(
                    true,
                    "Login successful",
                    "ADMIN",
                    token
            );
        }


        return new LoginResponse(
                false,
                "Invalid username or password",
                null,
                null
        );
    }


    @PostMapping("/logout")
    public LogoutResponse logout(
            @RequestHeader(
                    value = "Authorization",
                    required = false
            )
            String authorization) {

        if (authorization != null
                && authorization.startsWith("Bearer ")) {

            String token =
                    authorization.substring(7);

            sessionManager.removeSession(token);
        }


        return new LogoutResponse(
                true,
                "Logout successful"
        );
    }


    public record LoginRequest(
            String username,
            String password
    ) {
    }


    public record LoginResponse(
            boolean success,
            String message,
            String role,
            String token
    ) {
    }


    public record LogoutResponse(
            boolean success,
            String message
    ) {
    }
}