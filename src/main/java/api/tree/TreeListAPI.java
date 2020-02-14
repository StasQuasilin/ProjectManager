package api.tree;

import constants.API;
import controllers.ServletAPI;
import entity.project.Task;
import org.json.simple.JSONObject;
import services.hibernate.Hibernator;
import utils.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 26.02.2019.
 */
@WebServlet(API.Tree.LIST)
public class TreeListAPI extends ServletAPI {

    private static final Hibernator hibernator = Hibernator.getInstance();
    private static final String PROJECT = "project";
    private static final String TASK = "task";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null) {
            long selected = (long) body.get("selected");
            Task task;
            if (selected == -1) {
                task = null;
            } else {
                task = hibernator.get(Task.class, "id", selected);
            }
            final HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("owner", getUid(req));
            parameters.put("parent", task);

            JSONObject json = JsonParser.toJson(task, hibernator.query(Task.class, parameters));
            write(resp, json.toJSONString());
        } else {
            write(resp, ERROR);
        }
    }
}
