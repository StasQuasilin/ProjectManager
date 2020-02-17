package api.tree;

import constants.API;
import controllers.ServletAPI;
import entity.project.Task;
import entity.project.TaskStatus;
import entity.user.User;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 26.02.2019.
 */
@WebServlet(API.Tree.EDIT_TASK)
public class EditTaskAPI extends ServletAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            System.out.println(body);
            long id = -1;
            if(body.containsKey("id")){
                id = (long) body.get("id");
            }
            Task task;
            if (id != -1) {
                task = hibernator.get(Task.class, "id", id);
            } else {
                task = new Task();
            }
            String title = String.valueOf(body.get("title"));
            task.setTitle(title);
            task.setParent(hibernator.get(Task.class, ID, body.get(PARENT)));
            task.setOwner(hibernator.get(User.class, ID, getUid(req)));
            task.setStatus(TaskStatus.active);
            hibernator.save(task);
//            write(resp, SUCCESS);
        } else {
//            write(resp, ERROR);
        }
    }
}
