package com.teamA.databaseRejuvenator;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.supplier.PersistenceUnit;

public class ResetPersistence {


    public static void main(String[] args) {
        try {
            PersistenceManager.createCleaner(PersistenceUnit.POSTGRES).clear();

        } catch (PersistenceFailure persistenceFailure) {
            persistenceFailure.printStackTrace();
        }
    }
}
