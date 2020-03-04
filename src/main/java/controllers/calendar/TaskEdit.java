package controllers.calendar;

import constants.API;
import constants.Branches;
import controllers.IModal;
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
 * Created by szpt_user045 on 18.02.2020.
 */
@WebServlet(Branches.TASK_EDIT)
public class TaskEdit extends IModal {
    private static final String _CONTENT = "/pages/calendar/taskEdit.jsp";
    private static final String _TITLE = "title.task.edit";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Task task = dao.getObjectById(Task.class, body.get(ID));
            req.setAttribute(TASK, task);

        }
        User user = getUser(req);
        req.setAttribute(PARENTS, dao.getTaskByUser(user, TaskStatus.active, null));
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(PAGE_CONTENT, _CONTENT);
        req.setAttribute(SAVE, API.TASK_EDIT);
        showModal(req, resp);
    }
}
