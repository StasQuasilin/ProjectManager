package controllers.goals;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.ModalWindow;
import entity.goal.Goal;
import entity.task.TaskStatistic;
import entity.user.User;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;
import utils.db.dao.tree.TaskDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(UrlLinks.GOAL_REMOVE)
public class GoalRemove extends ModalWindow {

    private static final String _TITLE_REMOVE = "title.goal.remove";
    private static final String _CONTENT_REMOVE = "/pages/goals/goalRemove.jsp";
    private static final String _TITLE_LEAVE = "title.goal.leave";
    private static final String _CONTENT_LEAVE = "/pages/goals/goalLeave.jsp";
    private final GoalDAO goalDAO = daoService.getGoalDAO();
    private final TaskDAO taskDAO = daoService.getTaskDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            final Goal goal = goalDAO.getGoal(body.get(ID));

            final User user = getUser(req);
            req.setAttribute(GOAL, goal);

            if (goal.getOwner().equals(user)) {
                req.setAttribute(REMOVE, ApiLinks.GOAL_REMOVE);
                req.setAttribute(TITLE, _TITLE_REMOVE);
                req.setAttribute(CONTENT, _CONTENT_REMOVE);
                final TaskStatistic statistic = taskDAO.getStatistic(goal.getTitle().getId());
                req.setAttribute(STATISTIC, statistic);
            } else {
                req.setAttribute(REMOVE, ApiLinks.LEAVE_GOAL);
                req.setAttribute(TITLE, _TITLE_LEAVE);
                req.setAttribute(CONTENT, _CONTENT_LEAVE);
            }
            show(req, resp);
        }
    }
}
