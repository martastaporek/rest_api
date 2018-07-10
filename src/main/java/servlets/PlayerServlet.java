package servlets;

import com.google.gson.Gson;
import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.player.Player;
import com.teamA.data.player.PlayerService;


import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PlayerServlet extends HttpServlet {


    @Inject
    private PlayerService playerService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        String id = req.getPathInfo();
        Player player;
        try {
            player = playerService.loadPlayer(String.valueOf(id));
        } catch (PersistenceFailure persistenceFailure) {
            persistenceFailure.printStackTrace();
            resp.sendError(400, "Wrong URL! Usage: http://host:8080/players/{id}");
            return;
        }

        if(id == null || id.equals("/")) {
            // tu można by zrobić wyciąganie wszystkich zawodników
        }
        out.println(gson.toJson(player));
    }
}