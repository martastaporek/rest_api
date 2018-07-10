package com.teamA.data.player;

import com.teamA.customExceptions.PersistenceFailure;

public interface PlayerService {

    Player createPlayer(String firstName, String lastName, String birthYear) throws PersistenceFailure;
    boolean savePlayer(Player player);
    Player loadPlayer(String id) throws PersistenceFailure;
}
