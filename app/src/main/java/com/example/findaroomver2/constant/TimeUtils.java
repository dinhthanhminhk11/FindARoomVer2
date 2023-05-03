package com.example.findaroomver2.constant;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class TimeUtils {
    public static String getTimeAgo(long time) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime target = LocalDateTime.ofEpochSecond(time / 1000, 0, ZoneOffset.UTC);
        long seconds = ChronoUnit.SECONDS.between(target, now);

        if (seconds < 60) {
            return "vài giây trước";
        } else if (seconds < 3600) {
            long minutes = seconds / 60;
            return minutes + " phút trước";
        } else if (seconds < 86400) {
            long hours = seconds / 3600;
            return hours + " giờ trước";
        } else if (seconds < 172800) {
            return "hôm qua";
        } else {
            long days = seconds / 86400;
            return days + " ngày trước";
        }
    }
}
