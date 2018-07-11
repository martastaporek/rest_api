package com.teamA.data.match;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.team.Team;
import com.teamA.data.team.TeamService;
import com.teamA.logger.Logger;
import com.teamA.serviceHelpers.ServiceTransactionHelper;

import javax.persistence.EntityManager;
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
        return serviceTransactionHelper.updateEntity(match);
    }

    @Override
    public boolean registerGoal(String matchId, String teamId) {

        try {
            Match match = loadMatch(matchId);
            Team team = null;  // todo need dependency to TeamService

        } catch (PersistenceFailure persistenceFailure) {
            logger.log(persistenceFailure);
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

        } catch (NumberFormatException ex) {
            logger.log(ex.getMessage());
            return false;
        }
        return serviceTransactionHelper.updateEntity(match);
    }

    @Override
    public boolean registerScore(String matchId, String firstTeamScore, String secondTeamScore) {

        try {
            Match match = loadMatch(matchId);
            return registerScore(match, firstTeamScore, secondTeamScore);

        } catch (PersistenceFailure persistenceFailure) {
            logger.log(persistenceFailure);
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
        return serviceTransactionHelper.updateEntity(match);
    }

    @Override
    public boolean changeLocation(String matchId, String location) {
        return false;
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
        return null;
    }
}
