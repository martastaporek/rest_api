package com.teamA.data.team;

import com.teamA.customExceptions.PersistenceFailure;

public interface TeamService {

    Team createTeam(String name) throws PersistenceFailure;
    boolean registerPlayer(String teamId, String playerId);
    Team loadTeam(String id) throws PersistenceFailure;
    Team loadTeamByName(String teamName) throws PersistenceFailure;
}
