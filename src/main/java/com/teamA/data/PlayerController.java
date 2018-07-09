package com.teamA.data;

import com.teamA.customExceptions.PersistenceFailure;

public interface PlayerController {

    Player createPlayer(String firstName, String lastName, String birthYear) throws PersistenceFailure;
    boolean savePlayer(Player player);
    Player loadPlayer(String id) throws PersistenceFailure;
}
