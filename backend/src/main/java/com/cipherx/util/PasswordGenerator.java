package com.cipherx.util;

import java.security.SecureRandom;

public class PasswordGenerator {

    private static final String CHARACTERS =
            "ABCDEFGHJKLMNPQRSTUVWXYZ" +
            "abcdefghijkmnopqrstuvwxyz" +
            "23456789@#$";

    private static final SecureRandom RANDOM =
            new SecureRandom();

    public static String generatePassword(int length) {

        if (length < 6) {
            throw new IllegalArgumentException(
                    "Password length must be at least 6"
            );
        }

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }
}