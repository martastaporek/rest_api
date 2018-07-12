package com.teamA.data.team;

import com.teamA.customExceptions.PersistenceFailure;

import java.util.List;

public interface TeamService {

    Team createTeam(String name) throws PersistenceFailure;
    boolean registerPlayer(String teamId, String playerId);
    Team loadTeam (String id) throws PersistenceFailure;
    List<Team> loadAllTeams() throws PersistenceFailure;
    boolean changeName(String teamId, String teamName) throws PersistenceFailure;
    boolean deleteTeam(String teamId) throws PersistenceFailure;
    Team loadTeam(String id) throws PersistenceFailure;
    Team loadTeamByName(String teamName) throws PersistenceFailure;
}
