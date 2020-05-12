package api.budget;

import constants.API;
import controllers.ServletAPI;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 26.02.2020.
 */
@WebServlet(API.FIND_PERSON)
public class FindPersonAPI extends ServletAPI{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            String key = String.valueOf(body.get(KEY));

        }
    }
}
