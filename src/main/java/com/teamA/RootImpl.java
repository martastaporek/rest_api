package com.teamA;

import com.teamA.data.match.Match;
import com.teamA.data.match.MatchService;
import com.teamA.data.match.MatchServiceImpl;
import com.teamA.data.team.Team;
import com.teamA.serviceFactory.ServiceFactory;
import com.teamA.serviceFactory.ServiceFactoryImpl;
import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.player.Player;
import com.teamA.data.player.PlayerService;
import com.teamA.data.player.PlayerServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
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
        PlayerService playerService = serviceFactory.createPlayerService(PlayerServiceImpl.class);


        try {
//            Player player = playerService.createPlayer("Damian", "Xxxx", "1991");
//
//            System.out.println(player);
//
            Player loaded = playerService.loadPlayer("104");

            System.out.println(loaded);

            makeFunWithMatch(serviceFactory, entityManager);



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


    private void makeFunWithMatch(ServiceFactory serviceFactory, EntityManager entityManager) {

        MatchService matchService = serviceFactory.createMatchService(MatchServiceImpl.class);

//        EntityTransaction transaction = entityManager.getTransaction();
//        transaction.begin();
//
//        Team firstTeam = new Team("Jajka");
//        Team secondTeam = new Team("Lolki");
//        firstTeam.setId(10000);
//        secondTeam.setId(100001);
//
//        entityManager.persist(firstTeam);
//        entityManager.persist(secondTeam);
//
//        transaction.commit();

//        Match match = matchService.createMatch(firstTeam, secondTeam,"Cracow");

        Match match;
        try {
            match = matchService.loadMatch("2");
            System.out.println("Created match: " + match);

            matchService.changeLocation(match, "Poznan");
            matchService.registerScore(match, "5", "1");

            System.out.println(match);
        } catch (PersistenceFailure persistenceFailure) {
            persistenceFailure.printStackTrace();
        }

    }
}
