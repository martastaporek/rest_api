package com.teamA.customExceptions;

public class PersistenceFailure extends Exception {

    public PersistenceFailure(String message) {
        super(message);
    }

    public PersistenceFailure() {
        super("Persistence failure!");
    }
}
