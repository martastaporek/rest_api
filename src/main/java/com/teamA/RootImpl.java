package com.teamA;

import com.teamA.serviceFactory.ServiceFactory;
import com.teamA.serviceFactory.ServiceFactoryImpl;
import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.player.Player;
import com.teamA.data.player.PlayerService;
import com.teamA.data.player.PlayerServiceImpl;

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
        ServiceFactory serviceFactory = createServiceFactory(entityManager);
        PlayerService playerService = serviceFactory.createPlayerController(PlayerServiceImpl.class);

        try {
//            Player player = playerService.createPlayer("Damian", "Xxxx", "1991");
//
//            System.out.println(player);
//
            Player loaded = playerService.loadPlayer("104");

            System.out.println(loaded);

        } catch (PersistenceFailure creationFailure) {
            creationFailure.printStackTrace();
        }
    }


    private EntityManager createEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("mundialPU");
        return factory.createEntityManager();
    }

    private ServiceFactory createServiceFactory(EntityManager entityManager) {
        return ServiceFactoryImpl.create(entityManager);
    }
}
