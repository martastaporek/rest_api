package com.teamA.servlets;

import com.google.gson.JsonObject;
import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.match.Match;
import com.teamA.data.match.MatchService;
import com.teamA.parsers.JsonParser;
import com.teamA.servletHelper.RequestDataRetriver;
import com.teamA.servletSupplier.Supplier;

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

        JsonParser jsonParser = Supplier.deliverJsonParser();
        JsonObject jsonObject = jsonParser.parse(dataFromRequest);
        Match match = jsonParser.parse(jsonObject, Match.class);

        try {
            matchService.createMatch(match.getFirstTeam(), match.getSecondTeam(), match.getLocation());
        } catch (PersistenceFailure persistenceFailure) {
            persistenceFailure.printStackTrace();
        }
    }
}
