package com.cipherx.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public boolean validateAdmin(String username, String password) {
        return "admin".equals(username)
                && "admin123".equals(password);
    }
}