package com.teamA.data.team;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.player.Player;
import com.teamA.data.player.PlayerService;
import com.teamA.logger.Logger;
import com.teamA.serviceHelpers.ServiceTransactionHelper;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class TeamServiceImpl implements TeamService {

    private final EntityManager entityManager;
    private final ServiceTransactionHelper serviceTransactionHelper;
    private final Logger logger;
    private final PlayerService playerService;

    public TeamServiceImpl(EntityManager entityManager,
                           ServiceTransactionHelper serviceTransactionHelper,
                           Logger logger,
                           PlayerService playerService) {
        this.entityManager = entityManager;
        this.serviceTransactionHelper = serviceTransactionHelper;
        this.logger = logger;
        this.playerService = playerService;
    }

    @Override
    public Team createTeam(String name) throws PersistenceFailure {
        try {
            Team team = new Team(name);
            long maxId = serviceTransactionHelper.getMaxFreeId();
            team.setId(maxId + 1);
            if (! serviceTransactionHelper.saveNewEntity(team)) {
                throw new PersistenceFailure();
            }
            return team;
        } catch (PersistenceFailure persistenceFailure) {
            logger.log(persistenceFailure);
            String failureInfo = String.format("the team %s could not be created", name);
            throw new PersistenceFailure(failureInfo);
        }
    }

    @Override
    public boolean registerPlayer(String teamId, String playerId) {
        try{
            Team team = loadTeam(teamId);
            Player player = playerService.loadPlayer(playerId);
            team.getPlayers().add(player);
            player.setTeam(team);
            serviceTransactionHelper.updateEntity(player);
            return serviceTransactionHelper.updateEntity(team);
        } catch (Exception ex) {
            logger.log(ex);
            return false;
        }
    }

    @Override
    public Team loadTeam(String id) throws PersistenceFailure {
        try {
            long idAsLong = Long.parseLong(id);
            Team team = entityManager.find(Team.class, idAsLong);
            if (team == null) {
                throw new PersistenceFailure();
            }
            return team;
        } catch (NumberFormatException | PersistenceFailure ex) {
            logger.log(ex);
            String failureInfo = String.format("the team with id %s does not exist", id);
            throw new PersistenceFailure(failureInfo);
        }
    }

    @Override
    public List <Team> loadAllTeams() throws PersistenceFailure{


        try {
            List <Team> teams = entityManager.createQuery("select t from team t ", Team.class).getResultList();
            if(teams == null){
                throw new PersistenceFailure();
            }
            return teams;
        }catch (PersistenceFailure ex){
            logger.log(ex);
            throw new PersistenceFailure();
        }

    }

    @Override
    public boolean changeName(String teamId, String teamName) throws PersistenceFailure {
        try {
            Team team = loadTeam(teamId);
            team.setName(teamName);
            serviceTransactionHelper.updateEntity(team);
        } catch (PersistenceFailure persistenceFailure) {
            logger.log(persistenceFailure);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteTeam(String teamId) throws PersistenceFailure {
        try {
            Team team = loadTeam(teamId);
//            serviceTransactionHelper.deleteEntity(teamId)
        } catch (PersistenceFailure persistenceFailure) {
            logger.log(persistenceFailure);
            return false;
        }
        return true;
    }
}
