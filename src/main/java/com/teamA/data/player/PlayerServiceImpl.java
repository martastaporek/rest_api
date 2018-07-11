package com.teamA.data.player;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.logger.Logger;
import com.teamA.serviceHelpers.ServiceTransactionHelper;

import javax.persistence.EntityManager;
import java.util.Calendar;

public class PlayerServiceImpl implements PlayerService {

    private final EntityManager entityManager;
    private final ServiceTransactionHelper serviceTransactionHelper;
    private final Logger logger;

    public PlayerServiceImpl(EntityManager entityManager, ServiceTransactionHelper serviceTransactionHelper,
                             Logger logger) {
        this.entityManager = entityManager;
        this.serviceTransactionHelper = serviceTransactionHelper;
        this.logger = logger;
    }

    @Override
    public Player createPlayer(String firstName, String lastName, String birthYear) throws PersistenceFailure {

        try {
            int birthYearAsInt = Integer.parseInt(birthYear);
            long currentMaxId = serviceTransactionHelper.getMaxFreeId();
            Player player = new Player(firstName, lastName, birthYearAsInt);
            player.setId(currentMaxId+1);
            if (! serviceTransactionHelper.saveNewEntity(player)) {
                throw new PersistenceFailure();
            }
            return player;
        } catch (NumberFormatException | PersistenceFailure ex) {
            logger.log(ex);
            String failureInfo = String.format("the player %s %s could not be created", firstName, lastName);
            throw new PersistenceFailure(failureInfo);
        }
    }

    @Override
    public boolean changeFirstName(String playerId, String firstName) {
        try {
            Player player = loadPlayer(playerId);
            player.setFirstName(firstName);
            serviceTransactionHelper.updateEntity(player);
        } catch (PersistenceFailure persistenceFailure) {
            logger.log(persistenceFailure);
            return false;
        }
        return true;
    }

    @Override
    public boolean changeLastName(String playerId, String lastName) {
        try {
            Player player = loadPlayer(playerId);
            player.setLastName(lastName);
            serviceTransactionHelper.updateEntity(player);
        } catch (PersistenceFailure persistenceFailure) {
            logger.log(persistenceFailure);
            return false;
        }
        return true;
    }

    @Override
    public String getAge(String playerId) {
        try {
            Player player = loadPlayer(playerId);
            int birthYear = player.getBirthYear();
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int result = currentYear - birthYear;
            return String.valueOf(result);
        } catch (PersistenceFailure persistenceFailure) {
            logger.log(persistenceFailure);
            return "";
        }
    }

    @Override
    public String getFullName(String playerId) {
        try {
            Player player = loadPlayer(playerId);
            return String.format("%s %s", player.getFirstName(), player.getLastName());
        } catch (PersistenceFailure persistenceFailure) {
            logger.log(persistenceFailure);
            return "";
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
        } catch (NumberFormatException | PersistenceFailure ex) {
            logger.log(ex);
            String failureInfo = String.format("the player with id %s could not be created", id);
            throw new PersistenceFailure(failureInfo);
        }
    }
}
