package com.teamA;

import com.teamA.data.match.Match;
import com.teamA.data.match.MatchService;
import com.teamA.data.match.MatchServiceImpl;
import com.teamA.data.team.Team;
import com.teamA.data.team.TeamService;
import com.teamA.data.team.TeamServiceImpl;
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
        TeamService teamService = serviceFactory.createTeamService(TeamServiceImpl.class);


        try {
//            Player player = playerService.createPlayer("Pablo", "Horny", "1995");
//            Team team = teamService.createTeam("Poland");
//            Team teamEng = teamService.createTeam("England");

//            System.out.println(team);
//            System.out.println(teamEng);

//            System.out.println(player);

            Player loaded = playerService.loadPlayer("3");

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

    private void makeFunWithTeam(ServiceFactory serviceFactory, EntityManager entityManager) {

        TeamService teamService = serviceFactory.createTeamService(TeamServiceImpl.class);
        PlayerService playerService = serviceFactory.createPlayerService(PlayerServiceImpl.class);


        try {
            Player player = playerService.loadPlayer("1");
            Team team = teamService.createTeam("Poland");
//            teamService.registerPlayer()

        } catch (PersistenceFailure persistenceFailure) {
            persistenceFailure.printStackTrace();
        }


    }

    private void makeFunWithMatch(ServiceFactory serviceFactory, EntityManager entityManager) {

        MatchService matchService = serviceFactory.createMatchService(MatchServiceImpl.class);
        TeamService teamService = serviceFactory.createTeamService(TeamServiceImpl.class);







        try {

            Team team1 = teamService.createTeam("England");

            Team team2 = teamService.createTeam("Germany");

            Match match = matchService.createMatch(team1, team2,"Krakow");


//            match = matchService.loadMatch("2");
            System.out.println("Created match: " + match);

            matchService.changeLocation(match, "Poznan");
            matchService.registerScore(match, "5", "1");

            System.out.println(match);
        } catch (PersistenceFailure persistenceFailure) {
            persistenceFailure.printStackTrace();
        }

    }
}
