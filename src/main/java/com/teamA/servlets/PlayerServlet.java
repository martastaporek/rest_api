package com.teamA.servlets;

import com.google.gson.JsonObject;
import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.player.Player;
import com.teamA.data.player.PlayerService;
import com.teamA.parsers.JsonParser;
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
        JsonParser jsonParser = Supplier.deliverJsonParser();
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

        if(id == null || id.equals("/")) {
            // tu można by zrobić wyciąganie wszystkich zawodników
        }
        out.println(jsonParser.parse(player));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonParser jsonParser = Supplier.deliverJsonParser();
        PlayerService playerService = Supplier.deliverPlayerService(PlayerService.class);

        String dataFromRequest = getDataFromRequest(req);
        JsonObject jsonObject = jsonParser.parse(dataFromRequest);
        Player player = jsonParser.parse(jsonObject, Player.class);

        try {
            playerService.createPlayer(player.getFirstName(), player.getLastName(), String.valueOf(player.getBirthYear()));
        } catch (PersistenceFailure persistenceFailure) {
            persistenceFailure.printStackTrace();
        }
    }

    private String getDataFromRequest(HttpServletRequest req) throws IOException {
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = reader.readLine())!= null) {
            sb.append(line);
        }
        return sb.toString();
    }
}