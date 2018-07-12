package com.teamA.servlets;

import com.teamA.customExceptions.JsonFailure;
import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.match.Match;
import com.teamA.data.match.MatchService;
import com.teamA.data.team.Team;
import com.teamA.data.team.TeamService;
import com.teamA.supplier.Supplier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MatchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String id = req.getPathInfo();
        if(id == null || id.equals("/")) {
            List<Match> matchList;
            try {
                matchList = Supplier.deliverMatchService(MatchService.class).loadAll();
                resp.getWriter().println(Supplier.deliverJsonParser().parseList(matchList));
            } catch (PersistenceFailure | JsonFailure failure) {
                failure.printStackTrace();
                resp.sendError(400, "Wrong URL! Usage: http://localhost:8080/matches");
                return;
            }
        } else {
            id = id.replace("/", "");
            Match match;
            try {
                match = Supplier.deliverMatchService(MatchService.class).loadMatch(id);
                resp.getWriter().println(Supplier.deliverJsonParser().parse(match));
            } catch (PersistenceFailure | JsonFailure failure) {
                failure.printStackTrace();
                resp.sendError(400, "Wrong URL! Usage: http://localhost:8080/matches/{id} or match with this id: "
                        + id +
                        " is not in database");
                return;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MatchService matchService = Supplier.deliverMatchService(MatchService.class);
        String dataFromRequest = Supplier.deliverRequestDataRetriever().getDataFromRequest(req);

        try {
            Match match = Supplier.deliverJsonParser().parse(dataFromRequest, Match.class);
            matchService.createMatch(match.getFirstTeam(), match.getSecondTeam(), match.getLocation());
        } catch (PersistenceFailure | JsonFailure failure) {
            failure.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getPathInfo();
        if(id == null || id.equals("/")) {
            resp.sendError(405, "Method not allowed for this URL");
        } else {
            id = id.replace("/", "");
            Match match;
            try {
                match = Supplier.deliverMatchService(MatchService.class).loadMatch(id);
                String dataFromRequest = Supplier.deliverRequestDataRetriever().getDataFromRequest(req);
                Match matchFromRequest = Supplier.deliverJsonParser().parse(dataFromRequest, Match.class);

                Team firstTeam = Supplier.deliverTeamService(TeamService.class)
                        .loadTeamByName(matchFromRequest
                                .getFirstTeam()
                                .getName());
                Team secondTeam = Supplier.deliverTeamService(TeamService.class)
                        .loadTeamByName(matchFromRequest
                                .getSecondTeam()
                                .getName());
                matchFromRequest.setFirstTeam(firstTeam);
                matchFromRequest.setSecondTeam(secondTeam);


                if(isScoreChanged(match, matchFromRequest)){
                    if(!Supplier.deliverMatchService(MatchService.class)
                            .registerScore(
                            id,
                            String.valueOf(matchFromRequest.getFirstTeamScore()),
                            String.valueOf(matchFromRequest.getSecondTeamScore())
                    )) {
                        resp.sendError(409,"Conflict! Couldn't update score of the match");
                        return;
                    }
                }

                if(isDateChanged(match, matchFromRequest)){
                    if(!Supplier.deliverMatchService(MatchService.class)
                            .registerDate(id, matchFromRequest.getDate())){
                        resp.sendError(409,"Conflict! Couldn't update date of the match");
                        return;
                    }
                }

                if(isLocationChanged(match, matchFromRequest)) {
                    if(!Supplier.deliverMatchService(MatchService.class)
                            .setLocation(id, matchFromRequest.getLocation())) {
                        resp.sendError(409,"Conflict! Couldn't update location of the match");
                        return;
                    }
                }
            } catch (PersistenceFailure | JsonFailure failure) {
                failure.printStackTrace();
                resp.sendError(400, "Wrong URL! Usage: http://host:8080/matches/{id}");
                return;
            }
        }
    }

    private boolean isLocationChanged(Match match, Match matchFromRequest) {
        return !(match.getLocation().equals(matchFromRequest.getLocation())) && matchFromRequest.getLocation() != null;
    }

    private boolean isDateChanged(Match match, Match matchFromRequest) {
        return !(match.getDate().equals(matchFromRequest.getDate())) && matchFromRequest.getDate() != null;
    }

    private boolean isScoreChanged(Match match, Match matchFromRequest) {
        return  isScoreOfFirstTeamChanged(match, matchFromRequest)&&
                isScoreOfSecondTeamChanged(match, matchFromRequest);
    }

    private boolean isScoreOfFirstTeamChanged(Match match, Match matchFromRequest) {
        return (match.getFirstTeamScore() != matchFromRequest.getFirstTeamScore()
                && matchFromRequest.getFirstTeamScore() >= 0);
    }

    private boolean isScoreOfSecondTeamChanged(Match match, Match matchFromRequest) {
        return (match.getSecondTeamScore() != matchFromRequest.getFirstTeamScore()
                && matchFromRequest.getFirstTeamScore() >= 0);
    }
}
