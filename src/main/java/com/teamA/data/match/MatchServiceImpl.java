package com.teamA.data.match;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.player.Player;
import com.teamA.data.team.Team;
import com.teamA.data.team.TeamService;
import com.teamA.logger.Logger;
import com.teamA.serviceHelpers.ServiceTransactionHelper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class MatchServiceImpl implements MatchService {

    private final EntityManager entityManager;
    private final ServiceTransactionHelper serviceTransactionHelper;
    private final Logger logger;
    private final TeamService teamService;

    public MatchServiceImpl(EntityManager entityManager, ServiceTransactionHelper serviceTransactionHelper,
                            Logger logger,
                            TeamService teamService) {
        this.entityManager = entityManager;
        this.serviceTransactionHelper = serviceTransactionHelper;
        this.logger = logger;
        this.teamService = teamService;
    }

    @Override
    public Match createMatch(Team firstTeam, Team secondTeam, String location) throws PersistenceFailure {

        try {
            long currentMaxId = serviceTransactionHelper.getMaxFreeId();
            Match match = new Match(firstTeam, secondTeam, location);
            match.setId(currentMaxId+1);
            if (! serviceTransactionHelper.saveNewEntity(match)) {
                throw new PersistenceFailure();
            }
            return match;
        } catch (PersistenceFailure persistenceFailure) {
            logger.log(persistenceFailure);
            String failureInfo = String.format("the match %s %s could not be created", firstTeam, secondTeam);
            throw new PersistenceFailure(failureInfo);
        }
    }

    @Override
    public boolean registerGoal(String matchId, String teamId) {

        try {
            Match match = loadMatch(matchId);
            Team shooters = teamService.loadTeam(teamId);
            int score;
            Team team = match.getFirstTeam();
            if (shooters.getId() == team.getId()) {
                score = match.getFirstTeamScore();
                match.setFirstTeamScore(++score);
            } else {
                score = match.getSecondTeamScore();
                match.setSecondTeamScore(++score);
            }
            return serviceTransactionHelper.updateEntity(match);
        } catch (PersistenceFailure persistenceFailure) {
            logger.log(persistenceFailure);
            return false;
        }
    }

    @Override
    public boolean registerScore(String matchId, String firstTeamScore, String secondTeamScore) {

        try {
            Match match = loadMatch(matchId);
            int firstTeamScoreAsInt = Integer.parseInt(firstTeamScore);
            int secondTeamScoreAsInt = Integer.parseInt(secondTeamScore);
            match.setFirstTeamScore(firstTeamScoreAsInt);
            match.setSecondTeamScore(secondTeamScoreAsInt);
            return serviceTransactionHelper.updateEntity(match);

        } catch (Exception ex) {
            logger.log(ex);
            return false;
        }
    }

    @Override
    public boolean registerDate(String matchId, Date date) {

        try {
            Match match = loadMatch(matchId);
            match.setDate(date);
            return serviceTransactionHelper.updateEntity(match);
        } catch (PersistenceFailure persistenceFailure) {
            logger.log(persistenceFailure);
            return false;
        }
    }

    @Override
    public boolean setLocation(String matchId, String location) {
        try {
            Match match = loadMatch(matchId);
            match.setLocation(location);
            return serviceTransactionHelper.updateEntity(match);
        } catch (PersistenceFailure persistenceFailure) {
            logger.log(persistenceFailure);
            return false;
        }
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
        } catch (NumberFormatException | PersistenceFailure ex) {
            logger.log(ex);
            String failureInfo = String.format("the match with id %s could not be created", matchId);
            throw new PersistenceFailure(failureInfo);
        }
    }

    @Override
    public List<Match> loadAll() throws PersistenceFailure {
        try {
            TypedQuery query = entityManager.createQuery("SELECT e FROM match e", Match.class);
            @SuppressWarnings("unchecked")
            List<Match> matches = query.getResultList();
            return matches;
        } catch (Exception e) {
            logger.log(e);
            throw new PersistenceFailure(e.getMessage());
        }
    }

    @Override
    public boolean removeMatch(String matchId) {
        try {
            serviceTransactionHelper.removeEntity(matchId, Match.class);
            return true;
        } catch (PersistenceFailure persistenceFailure) {
            logger.log(persistenceFailure);
            return false;
        }
    }
}
