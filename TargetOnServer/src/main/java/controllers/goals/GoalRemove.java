package controllers.goals;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.ModalWindow;
import entity.goal.Goal;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(UrlLinks.GOAL_REMOVE)
public class GoalRemove extends ModalWindow {

    private static final String _TITLE = "title.goal.remove";
    private static final String _CONTENT = "/pages/goals/goalRemove.jsp";
    private final GoalDAO goalDAO = daoService.getGoalDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            final Goal goal = goalDAO.getGoal(body.get(ID));
            req.setAttribute(GOAL, goal);
            req.setAttribute(TITLE, _TITLE);
            req.setAttribute(CONTENT, _CONTENT);
            req.setAttribute(REMOVE, ApiLinks.GOAL_REMOVE);
            show(req, resp);
        }
    }
}
