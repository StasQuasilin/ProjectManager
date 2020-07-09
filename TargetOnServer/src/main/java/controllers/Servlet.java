package controllers;

import entity.user.User;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.json.JsonObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static constants.Keys.USER;
/**
 * Created by DELL on 05.07.2020.
 */
public abstract class Servlet extends HttpServlet {

    private JSONParser parser = new JSONParser();
    public JsonObject parseBody(HttpServletRequest req) throws IOException {
        try {
            return new JsonObject( parser.parse(req.getReader()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public User getUser(HttpServletRequest req){
        return (User) req.getSession().getAttribute(USER);
    }
}
