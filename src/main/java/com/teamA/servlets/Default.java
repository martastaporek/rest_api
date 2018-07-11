package com.teamA.servlets;





import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.player.Player;
import com.teamA.data.player.PlayerService;
import com.teamA.data.player.PlayerServiceImpl;
import com.teamA.data.team.TeamService;
import com.teamA.data.team.TeamServiceImpl;
import com.teamA.servletSupplier.Supplier;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;


public class Default extends HttpServlet {

    private static final long serialVersionUID = 1L;


    String dbName = "jdbc:postgresql://localhost/mundialdb";
    String dbDriver = "org.postgresql.Driver";
    String userName = "team_a";
    String password = "1234";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        try {
            Class.forName(dbDriver);
            Connection con = DriverManager.getConnection(dbName, userName, password);
            System.out.println("Got Connection");
//            makeFunWithMatch();
            makeFunWithPlayer();
//            makeFunWithTeam();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{

    }

    private void makeFunWithPlayer() {

        PlayerService playerService = Supplier.deliverPlayerService(PlayerServiceImpl.class);
        TeamService teamService = Supplier.deliverTeamService(TeamServiceImpl.class);
        Player loaded;
        try {

            Player newPlayer = playerService.createPlayer("Ja", "Twain", "1987");


        } catch (PersistenceFailure persistenceFailure) {
            persistenceFailure.printStackTrace();
        }
    }


//    private void makeFunWithTeam() {
//
//        TeamService teamService = Supplier.deliverTeamService(TeamServiceImpl.class);
//
//        System.out.println(teamService.registerPlayer("1001", "100") );
//        System.out.println(teamService.registerPlayer("1001", "101") );
//        System.out.println(teamService.registerPlayer("1001", "102") );
//        System.out.println(teamService.registerPlayer("1001", "103") );
//
//
//        try {
//            Team team = teamService.loadTeam("1001");
//
//            System.out.println(team);
//        } catch (PersistenceFailure persistenceFailure) {
//            persistenceFailure.printStackTrace();
//        }
//    }
//
//    private void makeFunWithMatch() {
//
//        MatchService matchService = Supplier.deliverMatchService(MatchServiceImpl.class);
//        TeamService teamService = Supplier.deliverTeamService(TeamServiceImpl.class);
//
//        try {
//
//            Team team1 = teamService.createTeam("San Escobar");
//
//            Team team2 = teamService.createTeam("San Escobar 2");
//
//            Match match = matchService.createMatch(team1, team2,"Warsaw");
//
//
//            System.out.println("Created match: " + match);
//
//            matchService.changeLocation(match, "Hamburg");
//            matchService.registerScore(match, "0", "2");
//
//            System.out.println(match);
//        } catch (PersistenceFailure persistenceFailure) {
//            persistenceFailure.printStackTrace();
//        }

//    }


}
