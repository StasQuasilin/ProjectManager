package controllers.goals;

import constants.ApiLinks;
import constants.Keys;
import constants.UrlLinks;
import controllers.ModalWindow;
import entity.goal.Goal;
import entity.user.User;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;
import utils.db.dao.user.FriendshipDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(UrlLinks.GOAL_MEMBERS)
public class GoalMembers extends ModalWindow {

    private static final String _TITLE = "title.goal.members";
    private static final String _CONTENT = "/pages/goals/goalMembers.jsp";
    private final GoalDAO goalDAO = daoService.getGoalDAO();
    private final FriendshipDAO friendshipDAO = daoService.getFriendshipDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            final User user = getUser(req);
            req.setAttribute(Keys.FRIENDS, friendshipDAO.getFriends(user));
            final Goal goal = goalDAO.getGoal(body.get(Keys.ID));
            req.setAttribute(Keys.GOAL, goal);
            req.setAttribute(Keys.MEMBERS, goalDAO.getGoalMembers(goal));
            req.setAttribute(Keys.SAVE, ApiLinks.SAVE_GOAL_MEMBERS);
            req.setAttribute(Keys.TITLE, _TITLE);
            req.setAttribute(Keys.CONTENT, _CONTENT);
            show(req, resp);
        }
    }
}
