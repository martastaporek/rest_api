package com.teamA.servlets;

import com.teamA.customExceptions.JsonFailure;
import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.match.Match;
import com.teamA.data.match.MatchService;
import com.teamA.servletHelper.RequestDataRetriver;
import com.teamA.supplier.Supplier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MatchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MatchService matchService = Supplier.deliverMatchService(MatchService.class);
        RequestDataRetriver dataRetriever = Supplier.deliverRequestDataRetriver();

        String dataFromRequest = dataRetriever.getDataFromRequest(req);

        try {
            Match match = Supplier.deliverJsonParser().parse(dataFromRequest, Match.class);
            matchService.createMatch(match.getFirstTeam(), match.getSecondTeam(), match.getLocation());
        } catch (PersistenceFailure | JsonFailure failure) {
            failure.printStackTrace();
        }
    }
}
