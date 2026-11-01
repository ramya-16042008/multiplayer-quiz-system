package com.cipherx.util;

public class ValidationUtil {

    public static boolean isValidUsername(String username) {
        return username != null &&
                username.matches("^[a-zA-Z0-9_]{3,20}$");
    }

    public static boolean isValidEmail(String email) {
        return email != null &&
                email.matches(
                        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
                );
    }

    public static boolean isValidPassword(String password) {
        return password != null &&
                password.length() >= 6;
    }

    public static boolean isValidQuizCode(String quizCode) {
        return quizCode != null &&
                quizCode.matches("^[A-Z0-9]{6}$");
    }

    public static boolean isValidTime(int seconds) {
        return seconds > 0;
    }
}