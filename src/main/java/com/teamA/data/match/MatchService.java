package com.teamA.data.match;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.player.Player;
import com.teamA.data.team.Team;

import java.util.List;

public interface MatchService {

    Player createMatch(Team firstTeam, Team secondTeam, String location) throws PersistenceFailure;
    boolean registerGoal(Team shooters) throws PersistenceFailure;
    boolean registerScore(String firstTeamScore, String secondTeamScore) throws PersistenceFailure;
    boolean changeLocation(Match match, String location) throws PersistenceFailure;
    boolean saveMatch(Match match);
    Match loadMatch(String id) throws PersistenceFailure;
    List<Match> loadAll() throws PersistenceFailure;

}
