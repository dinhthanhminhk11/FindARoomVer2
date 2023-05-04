package com.example.findaroomver2.constant;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.TimeZone;

public class TimeUtils {
    public static String getTimeAgo(long time) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        cal.setTimeZone(tz);
        cal.setTimeInMillis(time);
        Calendar now = Calendar.getInstance(tz);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm, dd/MM/yyyy");
        dateFormat.setTimeZone(tz);
        String timeString = dateFormat.format(cal.getTime());
        if (now.get(Calendar.DATE) == cal.get(Calendar.DATE)) {
            long diff = now.getTimeInMillis() - cal.getTimeInMillis();
            int hours = (int) (diff / (60 * 60 * 1000));
            if (hours > 0) {
                timeString = hours + " giờ trước";
            } else {
                int minutes = (int) (diff / (60 * 1000));
                if (minutes > 0) {
                    timeString = minutes + " phút trước";
                } else {
                    timeString = "vừa xong";
                }
            }
        } else if (now.get(Calendar.DATE) - cal.get(Calendar.DATE) == 1) {
            timeString = "Hôm qua";
        }
        return timeString;
    }
}
