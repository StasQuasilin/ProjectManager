package controllers.task;

import constants.API;
import constants.Branches;
import controllers.IModal;
import entity.project.Task;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//@WebServlet(Branches.TASK_REMOVE)
public class TaskRemove extends IModal {
    private static final String _TITLE = "title.task.remove";
    private static final String _CONTENT = "/pages/task/taskRemove.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Task task = dao.getObjectById(Task.class, body.get(ID));
            List<Task> children = dao.getTaskByUserAndParent(task.getOwner(), task);
            req.setAttribute(TASK, task);
            req.setAttribute(CHILDREN, children);
            req.setAttribute(TITLE, _TITLE);
            req.setAttribute(PAGE_CONTENT, _CONTENT);
            req.setAttribute(REMOVE, API.TASK_REMOVE);
            show(req, resp);
        }
    }
}
