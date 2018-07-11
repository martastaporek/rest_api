package com.teamA.servlets;

import com.google.gson.Gson;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.player.Player;
import com.teamA.data.player.PlayerService;
import com.teamA.servletSupplier.Supplier;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class PlayerServlet extends HttpServlet {




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        PlayerService playerService = Supplier.deliverPlayerService(PlayerService.class);
        resp.setContentType("application/json");

        String pathInfo = req.getPathInfo();
        String id = pathInfo.replace("/", "");

        Player player;
        try {
            player = playerService.loadPlayer(id);
        } catch (PersistenceFailure persistenceFailure) {
            persistenceFailure.printStackTrace();
            resp.sendError(400, "Wrong URL! Usage: http://localhost:8080/players/{id}");
            return;
        }
        Gson gson = new Gson();

        if(id == null || id.equals("/")) {
            // tu można by zrobić wyciąganie wszystkich zawodników
        }
        out.println(gson.toJson(player));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();

        StringBuilder sb = new StringBuilder();
        String line;
        while((line = reader.readLine())!= null) {
            sb.append(line);
        }

        Gson gson = new Gson();
        Player player = gson.fromJson(sb.toString(), Player.class);
        System.out.println(player);
        PlayerService playerService = Supplier.deliverPlayerService(PlayerService.class);
        try {
            playerService.createPlayer(player.getFirstName(), player.getLastName(), String.valueOf(player.getBirthYear()));
        } catch (PersistenceFailure persistenceFailure) {
            persistenceFailure.printStackTrace();
        }
    }
}