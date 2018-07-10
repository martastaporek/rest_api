package com.teamA.data.match;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.team.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Date;
import java.util.List;

public class MatchServiceImpl implements MatchService {

    private final EntityManager entityManager;
//    private final TeamService!!!

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
    public boolean registerGoal(Match match, Team shooters) {

        int score;
        Team team = match.getFirstTeam();
        if (shooters.getId() == team.getId()) {
            score = match.getFirstTeamScore();
            match.setFirstTeamScore(++score);
        } else {
            score = match.getSecondTeamScore();
            match.setSecondTeamScore(++score);
        }
        return save(match);
    }

    @Override
    public boolean registerGoal(String matchId, String teamId) {

        try {
            Match match = loadMatch(matchId);
            Team team = null;  // todo need dependency to TeamService

        } catch (PersistenceFailure persistenceFailure) {
            persistenceFailure.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean registerScore(Match match, String firstTeamScore, String secondTeamScore) {

        try {
            int firstTeamScoreAsInt = Integer.parseInt(firstTeamScore);
            int secondTeamScoreAsInt = Integer.parseInt(secondTeamScore);

            match.setFirstTeamScore(firstTeamScoreAsInt);
            match.setSecondTeamScore(secondTeamScoreAsInt);

        } catch (NumberFormatException notUsed) {
            return false;
        }
        return save(match);
    }

    @Override
    public boolean registerScore(String matchId, String firstTeamScore, String secondTeamScore) {

        try {
            Match match = loadMatch(matchId);
            return registerScore(match, firstTeamScore, secondTeamScore);

        } catch (PersistenceFailure persistenceFailure) {
            persistenceFailure.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean registerDate(String matchId, Date date) {

        return false;
    }

    @Override
    public boolean registerDate(Match match, Date date) {
        return false;
    }

    @Override
    public boolean changeLocation(Match match, String location) {

        match.setLocation(location);
        return save(match);
    }

    @Override
    public boolean changeLocation(String matchId, String location) {
        return false;
    }

    @Override
    public boolean saveMatch(Match match) {
        return save(match);
    }

    @Override
    public Match loadMatch(String matchId) throws PersistenceFailure {
        try {
            long matchIdAsLong = Long.parseLong(matchId);
            Match match = entityManager.find(Match.class, matchIdAsLong);
            if (match == null) {
                throw new PersistenceFailure();
            }
            return match;
        } catch (NumberFormatException | PersistenceFailure notUsed) {
            String failureInfo = String.format("the match with id %s could not be created", matchId);
            throw new PersistenceFailure(failureInfo);
        }
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
