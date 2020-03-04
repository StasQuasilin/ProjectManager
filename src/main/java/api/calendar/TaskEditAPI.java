package api.calendar;

import api.socket.UpdateUtil;
import constants.API;
import controllers.ServletAPI;
import entity.project.Task;
import entity.project.TaskStatus;
import entity.project.TaskType;
import entity.user.User;
import org.json.simple.JSONObject;
import services.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 18.02.2020.
 */
@WebServlet(API.TASK_EDIT)
public class TaskEditAPI extends ServletAPI {

    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            Task task = dao.getObjectById(Task.class, body.get(ID));
            User user = getUser(req);

            if (task == null){
                task = new Task();
                task.setOwner(user);
                task.setStatus(TaskStatus.progressing);
                task.setType(TaskType.once);
            }

            String title = String.valueOf(body.get(TITLE));
            task.setTitle(title);

            Task parent = dao.getObjectById(Task.class, body.get(PARENT));
            task.setParent(parent);

            dao.save(task);
            JSONObject json = new SuccessAnswer(RESULT, task.getId()).toJson();
            write(resp, json.toJSONString());
            pool.put(json);

            updateUtil.onSave(task);
        }
    }
}
