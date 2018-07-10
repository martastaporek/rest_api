package com.teamA.data.team;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.player.Player;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class TeamServiceImpl implements TeamService {

    private final EntityManager entityManager;

    public TeamServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
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
    public boolean setPlayers(List<Player> players) {
        return false;
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
            return entityManager.createQuery("select max(p.id) from team p", Long.class).getSingleResult();
        } catch (NullPointerException notUsed) {
            return 1;
        }
    }
}
