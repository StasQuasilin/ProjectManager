package api.tree;

import constants.API;
import controllers.ServletAPI;
import entity.project.Task;
import entity.project.TaskStatus;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 30.05.2019.
 */
@WebServlet(API.Task.STATUS)
public class ChangeStatusAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null) {
//            Task task = hibernator.get(Task.class, "id", body.get("id"));
            TaskStatus status = TaskStatus.valueOf(String.valueOf(body.get("status")));
//            task.setStatus(status);
//            hibernator.save(task);
            write(resp, SUCCESS);
        } else {
            write(resp, ERROR);
        }
    }
}
