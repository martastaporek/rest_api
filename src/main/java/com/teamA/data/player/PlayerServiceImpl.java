package com.teamA.data.player;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.serviceHelpers.ServiceTransactionHelper;

import javax.persistence.EntityManager;

public class PlayerServiceImpl implements PlayerService {

    private final EntityManager entityManager;
    private final ServiceTransactionHelper serviceTransactionHelper;

    public PlayerServiceImpl(EntityManager entityManager, ServiceTransactionHelper serviceTransactionHelper) {
        this.entityManager = entityManager;
        this.serviceTransactionHelper = serviceTransactionHelper;
    }


    @Override
    public Player createPlayer(String firstName, String lastName, String birthYear) throws PersistenceFailure {

        try {
            int birthYearAsInt = Integer.parseInt(birthYear);
            long currentMaxId = serviceTransactionHelper.getMaxFreeId();
            Player player = new Player(firstName, lastName, birthYearAsInt);
            player.setId(currentMaxId+1);
            if (! serviceTransactionHelper.saveEntity(player)) {
                throw new PersistenceFailure();
            }
            return player;
        } catch (NumberFormatException | PersistenceFailure notUsed) {
            String failureInfo = String.format("the player %s %s could not be created", firstName, lastName);
            throw new PersistenceFailure(failureInfo);
        }
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
        } catch (NumberFormatException | PersistenceFailure notUsed) {
            String failureInfo = String.format("the player with id %s could not be created", id);
            throw new PersistenceFailure(failureInfo);
        }
    }
}
