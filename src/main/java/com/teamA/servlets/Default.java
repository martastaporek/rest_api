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


    private String dbName = "jdbc:postgresql://localhost/mundialDb";
    private String dbDriver = "org.postgresql.Driver";
    private String userName = "team_a";
    private String password = "1234";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        try {
            Class.forName(dbDriver);
            Connection con = DriverManager.getConnection(dbName, userName, password);
            System.out.println("Got Connection");
//           //method to load data

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
