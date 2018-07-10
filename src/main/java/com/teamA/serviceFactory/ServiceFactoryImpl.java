package com.teamA.serviceFactory;

import com.teamA.data.match.MatchService;
import com.teamA.data.match.MatchServiceImpl;
import com.teamA.data.player.PlayerService;
import com.teamA.data.player.PlayerServiceImpl;
import com.teamA.data.team.TeamService;
import com.teamA.data.team.TeamServiceImpl;
import com.teamA.logger.Logger;
import com.teamA.serviceHelpers.ServiceTransactionHelper;

import javax.persistence.EntityManager;

public class ServiceFactoryImpl implements ServiceFactory {


    private final EntityManager entityManager;
    private final ServiceTransactionHelper serviceTransactionHelper;
    private final Logger logger;

    public static ServiceFactory create(EntityManager entityManager,
                                        ServiceTransactionHelper serviceTransactionHelper,
                                        Logger logger) {
        return new ServiceFactoryImpl(entityManager, serviceTransactionHelper, logger);
    }

    private ServiceFactoryImpl(EntityManager entityManager,
                               ServiceTransactionHelper serviceTransactionHelper,
                               Logger logger) {
        this.entityManager = entityManager;
        this.serviceTransactionHelper = serviceTransactionHelper;
        this.logger = logger;
    }

    @Override
    public <T extends PlayerService> PlayerService createPlayerService(Class<T> type) {

        String typeString = type.getSimpleName();
        PlayerService service;
        switch (typeString) {
            case("PlayerControllerImpl"):
                service = new PlayerServiceImpl(entityManager, serviceTransactionHelper, logger);
                break;
            default:
                service = new PlayerServiceImpl(entityManager, serviceTransactionHelper, logger);
                break;
        }
        return service;
    }

    @Override
    public <T extends MatchService> MatchService createMatchService(Class<T> type) {

        TeamService teamService = createTeamService(TeamServiceImpl.class);
        String typeString = type.getSimpleName();
        MatchService service;
        switch (typeString) {
            case("MatchServiceImpl"):
                service = new MatchServiceImpl(entityManager, serviceTransactionHelper, logger, teamService);
                break;
            default:
                service = new MatchServiceImpl(entityManager, serviceTransactionHelper, logger, teamService);
                break;
        }
        return service;
    }

    @Override
    public <T extends TeamService> TeamService createTeamService(Class<T> type) {
        PlayerService playerService = createPlayerService(PlayerService.class);
        String typeString = type.getSimpleName();
        TeamService service;
        switch (typeString) {
            case("TeamServiceImpl"):
                service = new TeamServiceImpl(entityManager, serviceTransactionHelper, logger, playerService);
                break;
            default:
                service = new TeamServiceImpl(entityManager, serviceTransactionHelper, logger, playerService);
                break;
        }
        return service;
    }
}
