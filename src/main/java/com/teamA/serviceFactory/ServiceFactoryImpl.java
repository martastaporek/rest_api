package com.teamA.serviceFactory;

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
    public <T extends PlayerService> PlayerService createPlayerController(Class<T> type) {

        String typeString = type.getSimpleName();
        PlayerService controller;
        switch (typeString) {
            case("PlayerControllerImpl"):
                controller = new PlayerServiceImpl(entityManager);
                break;
            default:
                controller = new PlayerServiceImpl(entityManager);
                break;
        }
        return controller;
    }
}
