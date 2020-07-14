package controllers.api;

import controllers.Servlet;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.ENCODING;

public abstract class API extends Servlet {

    public static final String SUCCESS_ANSWER = "{\"status\":\"success\"}";

    public void write(HttpServletResponse resp, JSONObject json) throws IOException {
        write(resp, json.toJSONString());
    }
    public void write(HttpServletResponse resp, String msg) throws IOException {
        resp.setCharacterEncoding(ENCODING);
        resp.getWriter().write(msg);
    }
}
