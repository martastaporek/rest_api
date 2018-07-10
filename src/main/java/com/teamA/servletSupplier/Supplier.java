package com.teamA.servletSupplier;

import com.teamA.data.match.MatchService;
import com.teamA.data.player.PlayerService;
import com.teamA.data.team.TeamService;
import com.teamA.serviceFactory.ServiceFactory;
import com.teamA.serviceFactory.ServiceFactoryImpl;
import com.teamA.serviceHelpers.ServiceTransactionHelper;
import com.teamA.serviceHelpers.ServiceTransactionHelperImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Supplier {


    private static String PERSISTENCE_UNIT = "mundialPU";
    private static EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory(PERSISTENCE_UNIT);
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();
    private static ServiceTransactionHelper serviceTransactionHelper = ServiceTransactionHelperImpl
            .create(entityManager);
    private static ServiceFactory serviceFactory = ServiceFactoryImpl.create(entityManager, serviceTransactionHelper);

    private static PlayerService playerService;
    private static TeamService teamService;
    private static MatchService matchService;

    public static EntityManager deliverEntityManager() {
        if (entityManager == null) {
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

    public static <T extends PlayerService> PlayerService deliverPlayerService(Class<T> type) {
        if (playerService == null) {
            playerService = serviceFactory.createPlayerService(type);
        }
        return playerService;
    }

    public static <T extends TeamService> TeamService deliverTeamService(Class<T> type) {
        if (teamService == null) {
            teamService = serviceFactory.createTeamService(type);
        }
        return teamService;
    }

    public static <T extends MatchService> MatchService deliverMatchService(Class<T> type) {
        if (matchService == null) {
            matchService = serviceFactory.createMatchService(type);
        }
        return matchService;
    }
}
