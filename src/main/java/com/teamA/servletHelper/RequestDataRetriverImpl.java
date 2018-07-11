package com.teamA.servletHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class RequestDataRetriverImpl implements RequestDataRetriver {
    @Override
    public String getDataFromRequest(HttpServletRequest req) throws IOException {
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = reader.readLine())!= null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
