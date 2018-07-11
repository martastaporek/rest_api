package com.teamA.databaseRejuvenator;

import com.teamA.customExceptions.PersistenceFailure;

public interface PersistenceCleaner {

    void clear() throws PersistenceFailure;

}
