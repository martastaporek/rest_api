package com.teamA.servletHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface RequestDataRetriver {
    String getDataFromRequest(HttpServletRequest req) throws IOException;
}