package controllers.kanban;

import constants.API;
import constants.Branches;
import controllers.IPage;
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
 * Created by szpt_user045 on 27.02.2020.
 */
@WebServlet(Branches.KANBAN)
public class KanbanView extends IPage {
    private static final String _TITLE = "title.kanban";
    private static final String _CONTENT = "/pages/kanban/kanbanList.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){

        }
        User user = getUser(req);
        req.setAttribute(TASKS, dao.getProjectsByOwner(user));
        req.setAttribute(TaskStatus.progressing.toString(), dao.getTaskByDoer(user, TaskStatus.progressing));
        req.setAttribute(TaskStatus.done.toString(), dao.getTaskByDoer(user, TaskStatus.done));
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(PAGE_CONTENT, _CONTENT);
        req.setAttribute(SAVE, API.TASK_EDIT);
        req.setAttribute(CHANGE_STATUS, API.CHANGE_TASK_STATUS);
        req.setAttribute(GET_SUB_TASKS, API.GET_SUB_TASK);
        showPage(req, resp);
    }
}
