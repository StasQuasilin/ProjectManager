package controllers;

import constants.Keys;
import entity.user.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import services.hibernate.Hibernator;
import services.hibernate.dbDAO;
import services.hibernate.dbDAOService;
import utils.JsonPool;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by szpt_user045 on 24.05.2019.
 */
public class IServlet extends HttpServlet implements Keys {

    final JSONParser parser = new JSONParser();
    public final JsonPool pool = JsonPool.getPool();
    public final dbDAO dao = dbDAOService.getDao();

    public Object getUid(HttpServletRequest req){
        return req.getSession().getAttribute("uid");
    }

    public User getUser(HttpServletRequest req){
        return (User) req.getSession().getAttribute(USER);
    }

    public JSONObject parseBody(HttpServletRequest req) {
        try {
            Object parse = parser.parse(req.getReader());
            return (JSONObject)parse ;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


}
