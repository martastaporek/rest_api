package com.teamA.logger;

public interface Logger {

    boolean log(String message);
    <T extends Exception> boolean log(T exception);
}
