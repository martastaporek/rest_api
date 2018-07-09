package com.teamA.controllersFactory;

import com.teamA.data.PlayerController;
import com.teamA.data.PlayerControllerImpl;

import javax.persistence.EntityManager;

public class ControllerFactoryImpl implements ControllerFactory {

    private final EntityManager entityManager;

    public static ControllerFactory create(EntityManager entityManager) {
        return new ControllerFactoryImpl(entityManager);
    }

    private ControllerFactoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public <T extends PlayerController> PlayerController createPlayerController(Class<T> type) {

        String typeString = type.getSimpleName();
        PlayerController controller;
        switch (typeString) {
            case("PlayerControllerImpl"):
                controller = new PlayerControllerImpl(entityManager);
                break;
            default:
                controller = new PlayerControllerImpl(entityManager);
                break;
        }
        return type.cast(controller);
    }
}
