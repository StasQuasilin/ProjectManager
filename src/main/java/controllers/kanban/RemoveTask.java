package controllers.kanban;

import constants.API;
import constants.Branches;
import controllers.IModal;
import entity.project.Task;
import org.json.simple.JSONObject;
import services.State;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.REMOVE_TASK)
public class RemoveTask extends IModal {
    private static final String _TITLE = "title.task.remove";
    private static final String _CONTENT = "/pages/kanban/taskRemove.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Task task = dao.getObjectById(Task.class, body.get(ID));
            req.setAttribute(TASK, task);
            req.setAttribute(CHILDREN, dao.getTasksByParent(getUser(req), task, State.ignore));
            req.setAttribute(REMOVE, API.TASK_REMOVE);
            req.setAttribute(TITLE, _TITLE);
            req.setAttribute(PAGE_CONTENT, _CONTENT);
            showModal(req, resp);
        }
    }
}
