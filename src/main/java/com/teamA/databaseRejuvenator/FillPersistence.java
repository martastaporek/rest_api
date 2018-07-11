package com.teamA.databaseRejuvenator;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.supplier.PersistenceUnit;

public class FillPersistence {

    public static void main(String[] args) {
        try {
            PersistenceManager.createFiller(PersistenceUnit.POSTGRES).rejuvenate();
        } catch (PersistenceFailure persistenceFailure) {
            persistenceFailure.printStackTrace();
        }
    }
}
