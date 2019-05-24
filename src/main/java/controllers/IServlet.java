package controllers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by szpt_user045 on 24.05.2019.
 */
public class IServlet extends HttpServlet {

    final JSONParser parser = new JSONParser();

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
