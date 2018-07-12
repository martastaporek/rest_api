package com.teamA.servlets;

import com.teamA.customExceptions.JsonFailure;
import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.player.Player;
import com.teamA.data.player.PlayerService;
import com.teamA.data.player.PlayerServiceImpl;
import com.teamA.parsers.JsonParser;
import com.teamA.supplier.Supplier;
import com.teamA.servletHelper.RequestDataRetriever;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PlayerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonParser jsonParser = Supplier.deliverJsonParser();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String id = req.getPathInfo();
        if(id == null || id.equals("/")) {
            List<Player> playerList;
            try {
                playerList = Supplier.deliverPlayerService(PlayerService.class).getAllPlayers();
                resp.getWriter().println(jsonParser.parseList(playerList));
            } catch (PersistenceFailure| JsonFailure  failure) {
                failure.printStackTrace();
            }
        } else {
            id = id.replace("/", "");
            Player player;
            try {
                player = getPlayerFromRequestData(id);
                resp.getWriter().println(jsonParser.parse(player));
            } catch (PersistenceFailure | JsonFailure failure) {
                failure.printStackTrace();
                resp.sendError(400, "Wrong URL! Usage: http://localhost:8080/players/{id}");
                return;
            }
        }
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
        String id = req.getPathInfo();
        id = id.replace("/","");
        Player player;
        try {
            player = getPlayerFromRequestData(id);
            String dataFromRequest = Supplier.deliverRequestDataRetriever().getDataFromRequest(req);
            Player playerFromRequest = Supplier.deliverJsonParser()
                    .parse(dataFromRequest, Player.class);

            if(isFirstNameChanged(player, playerFromRequest)) {
                playerService.changeFirstName(String.valueOf(player.getId()), playerFromRequest.getFirstName());
            }

            if(isLastNameChanged(player, playerFromRequest)) {
                playerService.changeLastName(String.valueOf(player.getId()), playerFromRequest.getLastName());
            }


        } catch (PersistenceFailure | JsonFailure failure) {
            failure.printStackTrace();
            resp.sendError(400, "Wrong URL! Usage: http://localhost:8080/players/{id}");
            return;
        }
    }

    private boolean isFirstNameChanged(Player player, Player playerFromRequest) {
        return !(player.getFirstName().equals(playerFromRequest.getFirstName())) && playerFromRequest.getFirstName() != null;
    }

    private boolean isLastNameChanged(Player player, Player playerFromRequest) {
        return !(player.getLastName().equals(playerFromRequest.getLastName())) && playerFromRequest.getLastName() != null;
    }
}