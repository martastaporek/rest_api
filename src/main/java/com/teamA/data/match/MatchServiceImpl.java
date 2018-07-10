package com.teamA.data.match;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.player.Player;
import com.teamA.data.team.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class MatchServiceImpl implements MatchService {

    private final EntityManager entityManager;

    public MatchServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Match createMatch(Team firstTeam, Team secondTeam, String location) throws PersistenceFailure {

        try {
            long currentMaxId = getMaxId();
            Match match = new Match(firstTeam, secondTeam, location);
            match.setId(currentMaxId+1);
            if (! save(match)) {
                throw new PersistenceFailure();
            }
            return match;
        } catch (PersistenceFailure notUsed) {
            String failureInfo = String.format("the match %s %s could not be created", firstTeam, secondTeam);
            throw new PersistenceFailure(failureInfo);
        }
    }

    @Override
    public boolean registerGoal(Match match, Team shooters) throws PersistenceFailure {
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

    private boolean save(Match match) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(match);
            transaction.commit();
        } catch (Exception notUsed) {
            rollBackTransaction(transaction);
            return false;
        }
        return true;
    }

    private long getMaxId() {
        try {
            return entityManager.createQuery("select max(m.id) from match m", Long.class).getSingleResult();
        } catch (NullPointerException notUsed) {
            return 1;
        }
    }

    private void rollBackTransaction(EntityTransaction transaction) {
        if (transaction != null) {
            try {
                transaction.rollback();
            } catch (Exception notUsed) {}
        }
    }
}
