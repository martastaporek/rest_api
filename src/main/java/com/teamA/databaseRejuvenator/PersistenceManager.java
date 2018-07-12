package com.teamA.databaseRejuvenator;

import com.github.javafaker.Faker;
import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.match.Match;
import com.teamA.data.match.MatchService;
import com.teamA.data.match.MatchServiceImpl;
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
import java.util.*;

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
        createEnglishTeam();
        createPolishTeam();
        createGermanTeam();
        createDutchTeam();
        createSpanishTeam();
        createFrenchTeam();

        registerMatches();

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
            session.createNativeQuery("drop table abstractentity cascade").executeUpdate();
            session.createNativeQuery("drop table player cascade").executeUpdate();
            session.createNativeQuery("drop table match cascade").executeUpdate();
            session.createNativeQuery("drop table team cascade").executeUpdate();
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

    private void createGermanTeam() throws PersistenceFailure {

        TeamService teamService = factory.createTeamService(TeamServiceImpl.class);
        Team germanTeam = teamService.createTeam("Germany");
        handleTeamCreation(new Faker(new Locale("de")), germanTeam, 11, true);
    }

    private void createSpanishTeam() throws PersistenceFailure {

        TeamService teamService = factory.createTeamService(TeamServiceImpl.class);
        Team spanishTeam = teamService.createTeam("Spain");
        handleTeamCreation(new Faker(new Locale("es")), spanishTeam, 11, true);
    }

    private void createFrenchTeam() throws PersistenceFailure {

        TeamService teamService = factory.createTeamService(TeamServiceImpl.class);
        Team frenchTeam = teamService.createTeam("France");
        handleTeamCreation(new Faker(new Locale("fr")), frenchTeam, 11, true);
    }

    private void createPolishTeam() throws PersistenceFailure {

        TeamService teamService = factory.createTeamService(TeamServiceImpl.class);
        Team polishTeam = teamService.createTeam("Poland");
        handleTeamCreation(new Faker(new Locale("pl")), polishTeam, 11, true);

    }

    private void createDutchTeam() throws PersistenceFailure {

        TeamService teamService = factory.createTeamService(TeamServiceImpl.class);
        Team dutchTeam = teamService.createTeam("Dutch");
        handleTeamCreation(new Faker(new Locale("nl")), dutchTeam, 11, true);

    }

    private void createEnglishTeam() throws PersistenceFailure {
        TeamService teamService = factory.createTeamService(TeamServiceImpl.class);
        Team englishTeam = teamService.createTeam("England");
        handleTeamCreation(new Faker(new Locale("en-GB")), englishTeam, 11, true);
    }

    private void registerMatches() throws PersistenceFailure {
        TeamService teamService = factory.createTeamService(TeamServiceImpl.class);
        MatchService matchService = factory.createMatchService(MatchServiceImpl.class);

        Team polishTeam = teamService.loadTeamByName("Poland");
        Team englishTeam = teamService.loadTeamByName("England");
        Team dutchTeam = teamService.loadTeamByName("Dutch");
        Team germanTeam = teamService.loadTeamByName("Germany");
        Team franchTeam = teamService.loadTeamByName("France");
        Team spanishTeam = teamService.loadTeamByName("Spain");

        Date today = new Date();
        handleMatchCreation(polishTeam, englishTeam, "Krakow", today, matchService);
        handleMatchCreation(dutchTeam, germanTeam, "Warsaw", today, matchService);
        handleMatchCreation(franchTeam, spanishTeam, "Katowice", today, matchService);

    }


    private void handleMatchCreation(Team firstTeam, Team secondTeam, String location, Date date,
                                         MatchService matchService) throws PersistenceFailure {

        Random chaos = new Random();
        int maxGoals = 6;
        String firstTeamScore = String.valueOf(chaos.nextInt(maxGoals));
        String secondTeamScore = String.valueOf(chaos.nextInt(maxGoals));
        Match match = matchService.createMatch(firstTeam, secondTeam, location);
        String matchId = String.valueOf(match.getId());
        matchService.registerScore(matchId, firstTeamScore, secondTeamScore);
        matchService.registerDate(matchId, date);

    }

    private void handleTeamCreation(Faker faker, Team team, int numberOfPlayers, boolean isMaleTeam)
            throws PersistenceFailure {

        TeamService teamService = factory.createTeamService(TeamServiceImpl.class);
        PlayerService playerService = factory.createPlayerService(PlayerServiceImpl.class);

        String teamId = String.valueOf(team.getId());
        Random chaos = new Random();
        int baseBirthDayYear = 1985;
        int ageRange = 15;

        for (int i=0; i<numberOfPlayers; i++) {
            int playerAge = chaos.nextInt(ageRange)+ baseBirthDayYear;
            String firstName;

            if (isMaleTeam) {

                firstName = getMaleName(faker);
            } else {
                firstName = getFemaleName(faker);
            }

            Player player = playerService.createPlayer(
                    firstName,
                    faker.name().lastName(),
                    String.valueOf(playerAge));

            String playerId = String.valueOf(player.getId());
            teamService.registerPlayer(teamId, playerId);
        }
    }

    private String getMaleName(Faker faker) {
        String firstName = "";
        while (firstName.length() < 2 || firstName.matches(".*[aeiou]$")) {
            firstName = faker.name().firstName();
        }
        return firstName;
    }

    private String getFemaleName(Faker faker) {
        String firstName = "";
        while (firstName.length() < 2 || ! firstName.matches(".*[aeiou]$")) {
            firstName = faker.name().firstName();
        }
        return firstName;
    }
}
