package controllers.api.goal;

import constants.ApiLinks;
import controllers.api.API;
import entity.goal.Goal;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;
import utils.json.JsonObject;
import utils.removers.GoalRemover;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.ID;

@WebServlet(ApiLinks.GOAL_REMOVE)
public class GoalRemoveAPI extends API {

    private final GoalDAO goalDAO = daoService.getGoalDAO();
    private final GoalRemover remover = new GoalRemover();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            final Goal goal = goalDAO.getGoal(body.get(ID));
            write(resp, SUCCESS_ANSWER);
            remover.remove(goal);

        } else {
            write(resp, SUCCESS_ANSWER);
        }
    }
}
