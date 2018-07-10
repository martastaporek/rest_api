package com.teamA.data.player;

import com.teamA.customExceptions.PersistenceFailure;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class PlayerControllerImpl implements PlayerController {

    private final EntityManager entityManager;

    public PlayerControllerImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Player createPlayer(String firstName, String lastName, String birthYear) throws PersistenceFailure {

        try {
            int birthYearAsInt = Integer.parseInt(birthYear);
            long currentMaxId = getMaxId();
            Player player = new Player(currentMaxId+1, firstName, lastName, birthYearAsInt);
            if (! save(player)) {
                throw new PersistenceFailure();
            }
            return player;
        } catch (NumberFormatException | PersistenceFailure notUsed) {
            String failureInfo = String.format("the player %s %s could not be created", firstName, lastName);
            throw new PersistenceFailure(failureInfo);
        }
    }

    @Override
    public boolean savePlayer(Player player) {
        return save(player);
    }

    @Override
    public Player loadPlayer(String id) throws PersistenceFailure {
        try {
            long idAsLong = Long.parseLong(id);
            Player player = entityManager.find(Player.class, idAsLong);
            if (player == null) {
                throw new PersistenceFailure();
            }
            return player;
        } catch (NumberFormatException notUsed) {
            String failureInfo = String.format("the player with id %s could not be created", id);
            throw new PersistenceFailure(failureInfo);
        }
    }

    private boolean save(Player player) {
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(player);
            transaction.commit();
        } catch (Exception notUsed) {
            notUsed.printStackTrace();
            return false;
        }
        return true;
    }

    private long getMaxId() {
        try {
            return entityManager.createQuery("select max(p.id) from Player p", Long.class).getSingleResult();
        } catch (NullPointerException notUsed) {
            return 1;
        }
    }
}
