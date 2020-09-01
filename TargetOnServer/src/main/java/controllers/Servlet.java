package controllers;

import entity.user.User;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.db.dao.daoService;
import utils.db.dao.user.UserDAO;
import utils.json.JsonObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

import static constants.Keys.USER;
/**
 * Created by DELL on 05.07.2020.
 */
public abstract class Servlet extends HttpServlet {

    private static final UserDAO userDao = daoService.getUserDAO();

    private JSONParser parser = new JSONParser();
    public JsonObject parseBody(HttpServletRequest req) throws IOException {
        try {
            return new JsonObject( parser.parse(req.getReader()));
        } catch (ParseException ignore) {}
        return null;
    }
    public User getUser(HttpServletRequest req){
        final HttpSession session = req.getSession();
        final Object attribute = session.getAttribute(USER);
        if (attribute == null){
            final String header = req.getHeader(USER);
            if (header != null){
                return userDao.getUserById(header);
            }
        } else {
            return (User) attribute;
        }
        return null;
    }
}
