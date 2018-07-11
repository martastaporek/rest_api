package com.teamA.databaseRejuvenator;

import com.teamA.customExceptions.PersistenceFailure;

public interface PersistenceFiller {

    void rejuvenate() throws PersistenceFailure;
}
