package com.teamA.data.team;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.player.Player;
import com.teamA.data.player.PlayerService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class TeamServiceImpl implements TeamService {

    private final EntityManager entityManager;
    private final PlayerService playerService;

    public TeamServiceImpl(EntityManager entityManager, PlayerService playerService) {
        this.entityManager = entityManager;
        this.playerService = playerService;
    }

    @Override
    public Team createTeam(String name) throws PersistenceFailure {
        try {
            Team team = new Team(name);
            team.setId(getMaxId() + 1);
            if (!save(team)) {
                throw new PersistenceFailure();
            }
            return team;
        } catch (PersistenceFailure notUsed) {
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
            playerService.savePlayer(player);
            save(team);
            return true;
        }catch (PersistenceFailure notUsed) {
            notUsed.printStackTrace();
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
        } catch (NumberFormatException | PersistenceFailure notUsed) {
            String failureInfo = String.format("the team with id %s does not exist", id);
            throw new PersistenceFailure(failureInfo);
        }
    }


    private boolean save(Team team) {
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(team);
            transaction.commit();
        } catch (Exception notUsed) {
            notUsed.printStackTrace();
            return false;
        }
        return true;
    }

    private long getMaxId() {
        try {
            return entityManager.createQuery("select max(e.id) from AbstractEntity e", Long.class).getSingleResult();
        } catch (NullPointerException notUsed) {
            return 1;
        }
    }
}
