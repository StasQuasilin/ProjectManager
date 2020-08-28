package controllers.kanban;

import constants.UrlLinks;
import controllers.Page;
import entity.goal.Goal;
import entity.task.TaskStatus;
import entity.task.TaskType;
import entity.user.User;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static constants.Keys.*;

@WebServlet(UrlLinks.KANBAN)
public class KanbanPage extends Page {

    private final GoalDAO goalDAO = daoService.getGoalDAO();

    private static final String _CONTENT = "/pages/kanban/kanbanPage.jsp";
    private static final String _TITLE = "title.kanban";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){

        }

        final User user = getUser(req);
        final List<Goal> goals = goalDAO.getGoals(user);
        req.setAttribute(GOALS, goals);

        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(STATUSES, new TaskStatus[]{TaskStatus.active, TaskStatus.progressing, TaskStatus.done});
        show(req, resp);
    }
}
