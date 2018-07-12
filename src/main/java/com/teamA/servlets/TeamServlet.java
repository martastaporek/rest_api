package com.teamA.servlets;

import com.teamA.customExceptions.JsonFailure;
import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.player.Player;
import com.teamA.data.player.PlayerService;
import com.teamA.data.team.Team;
import com.teamA.data.team.TeamService;
import com.teamA.parsers.JsonParser;
import com.teamA.servletHelper.RequestDataRetriver;
import com.teamA.supplier.Supplier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class TeamServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter out = resp.getWriter();
        JsonParser jsonParser = Supplier.deliverJsonParser();
        resp.setContentType("application/json");
        String id = getIdFromRequestData(req);
        if(id.equals("")) {
            try {

                List<Team> teams = getAllTeams();
                for(Team team: teams){
                    out.println(team);
                }
            }catch (PersistenceFailure ex){
                ex.printStackTrace();
                resp.sendError(400, "No players");
                return;
            }
        } else {
            Team team;
            try {
                team = getTeamFromRequestData(id);
                out.println(jsonParser.parse(team));
            } catch (PersistenceFailure | JsonFailure failure) {
                failure.printStackTrace();
                resp.sendError(400, "Wrong URL! Usage: http://localhost:8080/players/{id}");
                return;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeamService teamService = Supplier.deliverTeamService(TeamService.class);

        RequestDataRetriver dataRetriever = Supplier.deliverRequestDataRetriver();
        String dataFromRequest = dataRetriever.getDataFromRequest(req);

        try {

            Team team = Supplier.deliverJsonParser().parse(dataFromRequest, Team.class);
            teamService.createTeam(team.getName());

        } catch (PersistenceFailure | JsonFailure failure) {
            failure.printStackTrace();
        }
    }

    private List<Team> getAllTeams() throws PersistenceFailure {
        TeamService teamService = Supplier.deliverTeamService(TeamService.class);
        return teamService.loadAllTeams();
    }

    private String getIdFromRequestData(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        return pathInfo.replace("/", "");
    }
    private Team getTeamFromRequestData(String id) throws PersistenceFailure {
        TeamService teamService = Supplier.deliverTeamService(TeamService.class);
        return teamService.loadTeam(id);
    }

}
