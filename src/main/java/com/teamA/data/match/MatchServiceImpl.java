package com.teamA.data.match;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.player.Player;
import com.teamA.data.team.Team;

import javax.persistence.EntityManager;
import java.util.List;

public class MatchServiceImpl implements MatchService {

    private final EntityManager entityManager;

    public MatchServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Player createMatch(Team firstTeam, Team secondTeam, String location) throws PersistenceFailure {
        return null;
    }

    @Override
    public boolean registerGoal(Team shooters) throws PersistenceFailure {
        return false;
    }

    @Override
    public boolean registerScore(String firstTeamScore, String secondTeamScore) throws PersistenceFailure {
        return false;
    }

    @Override
    public boolean changeLocation(Match match, String location) throws PersistenceFailure {
        return false;
    }

    @Override
    public boolean saveMatch(Match match) {
        return false;
    }

    @Override
    public Match loadMatch(String id) throws PersistenceFailure {
        return null;
    }

    @Override
    public List<Match> loadAll() throws PersistenceFailure {
        return null;
    }
}
