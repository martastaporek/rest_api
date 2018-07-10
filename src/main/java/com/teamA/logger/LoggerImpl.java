package com.teamA.logger;

import com.teamA.helpers.DateTimer;

import java.io.IOException;
import java.util.Arrays;

public class LoggerImpl implements Logger {

    private final DateTimer dateTimer;
    private final LogWriter writer;

    public static Logger getInstance(DateTimer dateTimer,
                                     LogWriter writer) {
        return new LoggerImpl(dateTimer, writer);
    }

    private LoggerImpl(DateTimer dateTimer,
                       LogWriter writer) {
        this.dateTimer = dateTimer;
        this.writer = writer;
    }

    @Override
    public boolean log(String message) {
        String messageSeparator = "\n========report========\n";
        StringBuilder sb = new StringBuilder(messageSeparator);
        sb.append("Time: ");
        sb.append(dateTimer.getCurrentDateTime());
        sb.append("\n");
        sb.append("Report: ");
        sb.append(message);

        try {
            return writer.write(sb.toString());
        } catch (IOException e) {
            System.out.println("log failure, can't write to file:");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public <T extends Exception> boolean log(T exception) {
        String message = exception.getMessage() + " STACK TRACE: " + Arrays.toString(exception.getStackTrace());
        return log(message);
    }
}
