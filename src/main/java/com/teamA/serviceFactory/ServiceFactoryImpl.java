package com.teamA.serviceFactory;

import com.teamA.data.match.MatchService;
import com.teamA.data.match.MatchServiceImpl;
import com.teamA.data.player.PlayerService;
import com.teamA.data.player.PlayerServiceImpl;

import javax.persistence.EntityManager;

public class ServiceFactoryImpl implements ServiceFactory {


    private final EntityManager entityManager;

    public static ServiceFactory create(EntityManager entityManager) {
        return new ServiceFactoryImpl(entityManager);
    }

    private ServiceFactoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public <T extends PlayerService> PlayerService createPlayerService(Class<T> type) {

        String typeString = type.getSimpleName();
        PlayerService service;
        switch (typeString) {
            case("PlayerControllerImpl"):
                service = new PlayerServiceImpl(entityManager);
                break;
            default:
                service = new PlayerServiceImpl(entityManager);
                break;
        }
        return service;
    }

    @Override
    public <T extends MatchService> MatchService createMatchService(Class<T> type) {

        String typeString = type.getSimpleName();
        MatchService service;
        switch (typeString) {
            case("MatchServiceImpl"):
                service = new MatchServiceImpl(entityManager);
                break;
            default:
                service = new MatchServiceImpl(entityManager);
                break;
        }
        return service;
    }
}
