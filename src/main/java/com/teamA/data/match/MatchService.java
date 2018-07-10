package com.teamA.data.match;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.team.Team;

import java.time.LocalDate;
import java.util.List;

public interface MatchService {

    Match createMatch(Team firstTeam, Team secondTeam, String location) throws PersistenceFailure;
    boolean registerGoal(Match match, Team shooters);
    boolean registerGoal(String matchId, String teamId);
    boolean registerScore(Match match, String firstTeamScore, String secondTeamScore);
    boolean registerScore(String matchId, String firstTeamScore, String secondTeamScore);
    boolean registerDate(String matchId, LocalDate date);
    boolean registerDate(Match match, LocalDate date);
    boolean changeLocation(Match match, String location);
    boolean changeLocation(String matchId, String location);
    Match loadMatch(String matchId) throws PersistenceFailure;
    List<Match> loadAll() throws PersistenceFailure;

}
