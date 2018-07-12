package com.teamA.servlets;

import com.teamA.customExceptions.JsonFailure;
import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.match.Match;
import com.teamA.data.match.MatchService;
import com.teamA.servletHelper.RequestDataRetriever;
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
        String id = req.getPathInfo().replace("/", "");
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
            Match match;
            try {
                match = Supplier.deliverMatchService(MatchService.class).loadMatch(id);
                resp.getWriter().println(Supplier.deliverJsonParser().parse(match));
            } catch (PersistenceFailure | JsonFailure failure) {
                failure.printStackTrace();
                resp.sendError(400, "Wrong URL! Usage: http://localhost:8080/matches/{id}");
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
}
