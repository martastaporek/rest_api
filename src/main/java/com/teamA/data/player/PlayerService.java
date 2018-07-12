package com.teamA.data.player;

import com.teamA.customExceptions.PersistenceFailure;

import java.util.List;

public interface PlayerService {

    Player createPlayer(String firstName, String lastName, String birthYear) throws PersistenceFailure;
    boolean changeFirstName(String playerId, String firstName);
    boolean changeLastName(String playerId, String lastName);
    String getAge(String playerId);
    String getFullName(String playerId);
    Player loadPlayer(String id) throws PersistenceFailure;
    List<Player> getAllPlayers() throws PersistenceFailure;
    boolean removePlayer(String playerId);
}
