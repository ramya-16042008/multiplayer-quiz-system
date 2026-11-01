package com.cipherx.security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final SessionManager sessionManager;

    public AuthenticationFilter(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        // CORS preflight
        if ("OPTIONS".equalsIgnoreCase(method)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Public login
        if (path.equals("/api/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Public quiz information for players
        if (method.equalsIgnoreCase("GET")
                && path.matches("/api/quizzes/[0-9]+")) {

            filterChain.doFilter(request, response);
            return;
        }

        // Public quiz-code lookup
        if (method.equalsIgnoreCase("GET")
                && path.matches("/api/quizzes/code/.+")) {

            filterChain.doFilter(request, response);
            return;
        }

        // Public player join
        if (method.equalsIgnoreCase("POST")
                && path.startsWith("/api/players/join/")) {

            filterChain.doFilter(request, response);
            return;
        }

        // Check authenticated session
        String authorization =
                request.getHeader("Authorization");

        if (authorization != null
                && authorization.startsWith("Bearer ")) {

            String token =
                    authorization.substring(7);

            if (sessionManager.isValidSession(token)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        response.setStatus(
                HttpServletResponse.SC_UNAUTHORIZED);

        response.setContentType("application/json");

        response.getWriter().write(
                "{\"error\":\"Valid session required\"}"
        );
    }
}