package com.teamA;

import com.teamA.controllersFactory.ControllerFactory;
import com.teamA.controllersFactory.ControllerFactoryImpl;
import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.player.Player;
import com.teamA.data.player.PlayerController;
import com.teamA.data.player.PlayerControllerImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RootImpl implements Root {

    public static Root getInstance() {
        return new RootImpl();
    }

    private RootImpl() {}

    @Override
    public void run() {

        EntityManager entityManager = createEntityManager();
        ControllerFactory controllerFactory = createControllerFactory(entityManager);
        PlayerController playerController = controllerFactory.createPlayerController(PlayerControllerImpl.class);

        try {
            Player player = playerController.createPlayer("Johny", "Speedy", "1989");

            System.out.println(player);

            Player loaded = playerController.loadPlayer("1");

            System.out.println(loaded);

        } catch (PersistenceFailure creationFailure) {
            creationFailure.printStackTrace();
        }
    }


    private EntityManager createEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("mundialPU");
        return factory.createEntityManager();
    }

    private ControllerFactory createControllerFactory(EntityManager entityManager) {
        return ControllerFactoryImpl.create(entityManager);
    }
}
