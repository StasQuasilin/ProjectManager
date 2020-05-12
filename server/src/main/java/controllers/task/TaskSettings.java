package controllers.task;

import constants.API;
import constants.Branches;
import controllers.IModal;
import entity.task.Task;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.TASK_SETTING)
public class TaskSettings extends IModal {
    private static final String _TITLE = "title.task.settings";
    private static final String _CONTENT = "/pages/task/taskSettings.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            Task task = dao.getObjectById(Task.class, body.get(ID));
            req.setAttribute(TASK, task);
            req.setAttribute(TITLE, _TITLE);
            req.setAttribute(PAGE_CONTENT, _CONTENT);
            req.setAttribute(GET_SUB_TASKS, API.GET_SUB_TASK);
            req.setAttribute(SAVE, API.Tree.EDIT_TASK);
            show(req, resp);
        }
    }
}
