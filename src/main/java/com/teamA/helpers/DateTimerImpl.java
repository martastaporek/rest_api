package com.teamA.helpers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimerImpl implements DateTimer {

    private final String formatter;

    public static DateTimer getInstance() {
        return new DateTimerImpl();
    }

    public static DateTimer getInstance(String formatter) {
        return new DateTimerImpl(formatter);
    }

    private DateTimerImpl() {
        this.formatter = "yyyy-MM-dd HH:mm:ss";
    }

    private DateTimerImpl(String formatter) {
        this.formatter = formatter;
    }


    @Override
    public String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern(formatter);
        return now.format(format);
    }
}
