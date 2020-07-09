package controllers.api;

import controllers.Servlet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class API extends Servlet {

    public static final String SUCCESS_ANSWER = "{\"status\":\"success\"}";

    public void write(HttpServletResponse resp, String msg) throws IOException {
        resp.getWriter().write(msg);
    }
}
