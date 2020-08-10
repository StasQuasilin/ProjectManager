package controllers.tree;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.ModalWindow;
import entity.task.Task;
import entity.task.TimeLog;
import entity.user.User;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(UrlLinks.TASK_TIMER)
public class TaskTimerEdit extends ModalWindow {

    private static final String _TITLE = "title.task.timer";
    private static final String _CONTENT = "/pages/tree/taskTimer.jsp";
    private final TaskDAO taskDAO = daoService.getTaskDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            TimeLog timeLog = null;
            if (body.containKey(ID)){
                 timeLog = taskDAO.getTimeLog(body.get(ID));
            }
            if (timeLog == null){
                final User user = getUser(req);
                timeLog = taskDAO.getTimeLogByUser(user);

                if (timeLog == null){
                    final Task task = taskDAO.getTask(body.get(TASK));
                    req.setAttribute(TASK, task);
                } else {
                    req.setAttribute(TIME_LOG, timeLog);
                }
            }
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(FIND_TASK, ApiLinks.FIND_TASK);
        req.setAttribute(TIMER_START, ApiLinks.TIMER_START);
        req.setAttribute(TIMER_STOP, ApiLinks.TIMER_STOP);
        show(req, resp);
    }
}
