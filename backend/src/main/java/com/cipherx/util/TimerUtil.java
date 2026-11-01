package com.cipherx.util;

public class TimerUtil {

    public static boolean isTimeExpired(
            long startTime,
            int timeLimitSeconds) {

        long elapsedTime =
                (System.currentTimeMillis() - startTime) / 1000;

        return elapsedTime >= timeLimitSeconds;
    }

    public static long getRemainingSeconds(
            long startTime,
            int timeLimitSeconds) {

        long elapsedTime =
                (System.currentTimeMillis() - startTime) / 1000;

        long remaining = timeLimitSeconds - elapsedTime;

        return Math.max(remaining, 0);
    }
}