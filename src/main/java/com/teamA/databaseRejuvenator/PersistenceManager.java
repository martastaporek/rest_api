package com.teamA.databaseRejuvenator;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.player.Player;
import com.teamA.data.player.PlayerService;
import com.teamA.data.player.PlayerServiceImpl;
import com.teamA.data.team.Team;
import com.teamA.data.team.TeamService;
import com.teamA.data.team.TeamServiceImpl;
import com.teamA.serviceFactory.ServiceFactory;
import com.teamA.supplier.PersistenceUnit;
import com.teamA.supplier.Supplier;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import java.util.Random;

public class PersistenceManager implements PersistenceFiller, PersistenceCleaner {

    private final ServiceFactory factory;
    private final EntityManager entityManager;


    public static PersistenceFiller createFiller(PersistenceUnit database) {
        return new PersistenceManager(database);
    }

    public static PersistenceCleaner createCleaner(PersistenceUnit database) {
        return new PersistenceManager(database);
    }


    private PersistenceManager(PersistenceUnit database) {
        Supplier.setPersistenceUnit(database);

        this.factory = Supplier.deliverServiceFactory();
        this.entityManager = Supplier.deliverEntityManager();
    }

    @Override
    public void rejuvenate() throws PersistenceFailure {
        createFirstPlayer();
        createEnglishTeam();
        createPolishTeam();
        createGermanTeam();

        System.out.println("Done - DB populated!");
        System.exit(0);
    }


    @Override
    public void clear() throws PersistenceFailure {
        dropTables();
    }

    private void dropTables() throws PersistenceFailure {

        Session session = (Session) entityManager.getDelegate();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("drop table abstractentity cascade").executeUpdate();
            session.createSQLQuery("drop table player cascade").executeUpdate();
            session.createSQLQuery("drop table match cascade").executeUpdate();
            session.createSQLQuery("drop table team cascade").executeUpdate();

            transaction.commit();
        } catch (Exception notUsed) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new PersistenceFailure("Problem occurred!");
        }

        System.out.println("Done - tables dropped.");
        System.exit(0);
    }


    private void createFirstPlayer() throws PersistenceFailure {
        factory.createPlayerService(PlayerServiceImpl.class)
                .createPlayer("Example", "Example", "0000");
    }

    private void createGermanTeam() throws PersistenceFailure {

        TeamService teamService = factory.createTeamService(TeamServiceImpl.class);

        Team germanTeam = teamService.createTeam("Germany");

        String[] firstNames = {"Hans", "Albion", "Sebastian", "Adolf", "Adi", "Adrian", "Fritz",
                "Gustav", "Sigfried", "Harald", "Heinz"};
        String[] lastNames = {"Alba", "Schwein", "Gundula", "Mann", "Berliner", "Dam", "Renski", "Tiger",
                "Helm", "Pinot", "Brutt"};

        handleTeamCreation(firstNames, lastNames, germanTeam);
    }

    private void createPolishTeam() throws PersistenceFailure {

        TeamService teamService = factory.createTeamService(TeamServiceImpl.class);

        Team polishTeam = teamService.createTeam("Poland");

        String[] firstNames = {"Jan", "Marcin", "Tomasz", "Witek", "Mateusz", "Dominik", "Kuba",
                "Józef", "Piotr", "Wiesiek", "Konrad"};
        String[] lastNames = {"Kowalski", "Nowak", "Krakowski", "Wodnik", "Błaszczykowski",
                "Panek", "Jaglo", "Biały",
                "Szczotka", "Stolik", "Kuchar"};

        handleTeamCreation(firstNames, lastNames, polishTeam);

    }

    private void createEnglishTeam() throws PersistenceFailure {
        TeamService teamService = factory.createTeamService(TeamServiceImpl.class);

        Team englishTeam = teamService.createTeam("England");

        String[] firstNames = {"Johnny", "Bryan", "Conrad", "Peter", "Rob", "Domenic", "Daniel",
                "Alex", "Joseph", "Bruce", "Mateo"};
        String[] lastNames = {"Smith", "Robin", "Sherwood", "Nothingham", "White",
                "Bond", "Cly", "Black",
                "Horn", "Cook", "Yolo"};

        handleTeamCreation(firstNames, lastNames, englishTeam);
    }

    private void handleTeamCreation(String[] firstNames, String[] lastNames, Team team)
            throws PersistenceFailure {

        if (firstNames.length != 11 && lastNames.length != 11) {
            throw new IllegalArgumentException("Provide correct names!");
        }

        TeamService teamService = factory.createTeamService(TeamServiceImpl.class);
        PlayerService playerService = factory.createPlayerService(PlayerServiceImpl.class);

        String teamId = String.valueOf(team.getId());
        Random chaos = new Random();
        int baseBirthDayYear = 1985;
        int ageRange = 15;

        for (int i=0; i<firstNames.length; i++) {
            int playerAge = chaos.nextInt(ageRange)+ baseBirthDayYear;

            Player player = playerService.createPlayer( firstNames[i],
                    lastNames[i],
                    String.valueOf(playerAge));

            String playerId = String.valueOf(player.getId());
            teamService.registerPlayer(teamId, playerId);
        }
    }
}
