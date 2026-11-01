package com.cipherx.security;

import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    public boolean isAdmin(String role) {
        return "ADMIN".equalsIgnoreCase(role);
    }

    public boolean isPlayer(String role) {
        return "PLAYER".equalsIgnoreCase(role);
    }

    public boolean hasRole(
            String currentRole,
            String requiredRole) {

        return currentRole != null
                && requiredRole != null
                && currentRole.equalsIgnoreCase(requiredRole);
    }
}