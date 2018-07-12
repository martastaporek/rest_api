package com.teamA.customExceptions;

public class JsonFailure extends Exception {

    public JsonFailure(String message) {
        super(message);
    }

    public JsonFailure() {
        super("Persistence failure!");
    }
}
