package com.teamA.data.match;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.team.Team;

import java.util.Date;
import java.util.List;

public interface MatchService {

    Match createMatch(Team firstTeam, Team secondTeam, String location) throws PersistenceFailure;
    boolean registerGoal(Match match, Team shooters);
    boolean registerGoal(String matchId, String teamId);
    boolean registerScore(Match match, String firstTeamScore, String secondTeamScore);
    boolean registerScore(String matchId, String firstTeamScore, String secondTeamScore);
    boolean registerDate(String matchId, Date date);
    boolean registerDate(Match match, Date date);
    boolean changeLocation(Match match, String location);
    boolean changeLocation(String matchId, String location);
    Match loadMatch(String matchId) throws PersistenceFailure;
    List<Match> loadAll() throws PersistenceFailure;

}
