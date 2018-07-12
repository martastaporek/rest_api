package com.teamA.servlets;

import com.teamA.customExceptions.JsonFailure;
import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.player.Player;
import com.teamA.data.player.PlayerService;
import com.teamA.parsers.JsonParser;
import com.teamA.supplier.Supplier;
import com.teamA.servletHelper.RequestDataRetriever;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PlayerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        JsonParser jsonParser = Supplier.deliverJsonParser();
        resp.setContentType("application/json");
        String id = getIdFromRequestData(req);

        if(id == null || id.equals("/")) {
            // todo miejsce dla wyciagania wszystkich zawodników


        } else {
            Player player;
            try {
                player = getPlayerFromRequestData(id);
                out.println(jsonParser.parse(player));
            } catch (PersistenceFailure | JsonFailure failure) {
                failure.printStackTrace();
                resp.sendError(400, "Wrong URL! Usage: http://localhost:8080/players/{id}");
                return;
            }
        }
    }

    private String getIdFromRequestData(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        return pathInfo.replace("/", "");
    }

    private Player getPlayerFromRequestData(String id) throws PersistenceFailure {
        PlayerService playerService = Supplier.deliverPlayerService(PlayerService.class);
        return playerService.loadPlayer(id);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PlayerService playerService = Supplier.deliverPlayerService(PlayerService.class);
        RequestDataRetriever dataRetriever = Supplier.deliverRequestDataRetriever();
        String dataFromRequest = dataRetriever.getDataFromRequest(req);

        try {
            Player player = Supplier.deliverJsonParser()
                    .parse(dataFromRequest, Player.class);

            playerService.createPlayer(player.getFirstName(), player.getLastName(), String.valueOf(player.getBirthYear()));
        } catch (PersistenceFailure | JsonFailure failure) {
            failure.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PlayerService playerService = Supplier.deliverPlayerService(PlayerService.class);
        String id = getIdFromRequestData(req);
        Player player;
        try {
            player = getPlayerFromRequestData(id);
            String dataFromRequest = Supplier.deliverRequestDataRetriever().getDataFromRequest(req);
            Player playerFromRequest = Supplier.deliverJsonParser()
                    .parse(dataFromRequest, Player.class);

            if(!(player.getFirstName().equals(playerFromRequest.getFirstName())) && playerFromRequest.getFirstName() != null) {
                playerService.changeFirstName(String.valueOf(player.getId()), playerFromRequest.getFirstName());
            }

            if(!(player.getLastName().equals(playerFromRequest.getLastName())) && playerFromRequest.getLastName() != null) {
                playerService.changeLastName(String.valueOf(player.getId()), playerFromRequest.getLastName());
            }


        } catch (PersistenceFailure | JsonFailure failure) {
            failure.printStackTrace();
            resp.sendError(400, "Wrong URL! Usage: http://localhost:8080/players/{id}");
            return;
        }

    }
}