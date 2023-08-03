package com.padelmatchmanager.padelmatchmanager.utils;

import java.time.Duration;

public class TimeUtils {


    public static Duration parsePlayTime(String playTime) {
        if (playTime == null || playTime.isEmpty()) {
            return Duration.ZERO;
        }

        int hours = 0;
        int minutes = 0;
        if (playTime.contains("H")) {
            int indexH = playTime.indexOf("H");
            hours = Integer.parseInt(playTime.substring(2, indexH));
        }
        if (playTime.contains("M")) {
            int indexM = playTime.indexOf("M");
            minutes = Integer.parseInt(playTime.substring(playTime.indexOf("H") + 1, indexM));
        }

        // Calculate the total duration in seconds
        return Duration.ofHours(hours).plus(Duration.ofMinutes(minutes));
    }
}
