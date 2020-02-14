package api.tree;

import constants.API;
import controllers.ServletAPI;
import entity.project.Task;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 30.05.2019.
 */
@WebServlet(API.Task.DELETE)
public class DeleteTaskAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            hibernator.delete(hibernator.get(Task.class, "id", body.get("id")));
            write(resp, SUCCESS);
        } else {
            write(resp, ERROR);
        }
    }
}
