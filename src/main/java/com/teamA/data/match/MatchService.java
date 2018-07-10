package com.teamA.data.match;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.team.Team;

import java.util.List;

public interface MatchService {

    Match createMatch(Team firstTeam, Team secondTeam, String location) throws PersistenceFailure;
    boolean registerGoal(Match match, Team shooters);
    boolean registerScore(String firstTeamScore, String secondTeamScore);
    boolean changeLocation(Match match, String location);
    boolean saveMatch(Match match);
    Match loadMatch(String id) throws PersistenceFailure;
    List<Match> loadAll() throws PersistenceFailure;

}
