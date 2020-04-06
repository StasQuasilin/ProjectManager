package controllers.kanban;

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

@WebServlet(Branches.KANBAN_SHOW)
public class TaskShow extends IModal {
    private static final String _TITLE = "title.task.show";
    private static final String _CONTENT = "/pages/kanban/taskShow.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Task task = dao.getObjectById(Task.class, body.get(ID));
            req.setAttribute(TASK, task);
            req.setAttribute(TIMERS, dao.getTimeLogs(task));
            req.setAttribute(TITLE, _TITLE);
            req.setAttribute(PAGE_CONTENT, _CONTENT);
            req.setAttribute(TIMER_START, API.TIMER_START);
            req.setAttribute(TIMER_STOP, API.TIMER_STOP);
            show(req, resp);
        }
    }
}
