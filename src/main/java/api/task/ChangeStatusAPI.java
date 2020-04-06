package api.task;

import api.socket.UpdateUtil;
import constants.API;
import controllers.ServletAPI;
import entity.project.Task;
import entity.project.TaskStatus;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.TaskUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 02.03.2020.
 */
@WebServlet(API.CHANGE_TASK_STATUS)
public class ChangeStatusAPI extends ServletAPI {

    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            User user = getUser(req);
            Task task = dao.getObjectById(Task.class, body.get(ID));
            TaskStatus status = TaskStatus.valueOf(String.valueOf(body.get(STATUS)));
            task.setStatus(status);
            if (status == TaskStatus.progressing){
                task.setDoer(user);
            } else if (status == TaskStatus.active){
                task.setDoer(null);
            }
            dao.save(task);
            write(resp, SUCCESS);
            updateUtil.onSave(task);
            if (!task.isTop()) {
                TaskUtil.checkParenthood(task.getParent(), user);
            }
        }
    }
}
