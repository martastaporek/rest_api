package com.teamA.data.match;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.team.Team;

import java.util.Date;
import java.util.List;

public interface MatchService {

    Match createMatch(Team firstTeam, Team secondTeam, String location) throws PersistenceFailure;
    boolean registerGoal(String matchId, String teamId);
    boolean registerScore(String matchId, String firstTeamScore, String secondTeamScore);
    boolean registerDate(String matchId, Date date);
    boolean setLocation(String matchId, String location);
    Match loadMatch(String matchId) throws PersistenceFailure;
    List<Match> loadAll() throws PersistenceFailure;
    boolean removeMatch(String matchId) throws PersistenceFailure;

}
