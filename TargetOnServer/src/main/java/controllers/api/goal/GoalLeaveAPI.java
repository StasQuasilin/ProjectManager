package controllers.api.goal;

import constants.ApiLinks;
import controllers.api.API;
import entity.goal.Goal;
import entity.goal.GoalMember;
import entity.user.User;
import subscribe.Subscribe;
import utils.Updater;
import utils.answers.Answer;
import utils.answers.SuccessAnswer;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static constants.Keys.ID;

@WebServlet(ApiLinks.LEAVE_GOAL)
public class GoalLeaveAPI extends API {

    private final GoalDAO goalDAO = daoService.getGoalDAO();
    private final Updater updater = new Updater();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if(body != null){
            final Goal goal = goalDAO.getGoal(body.get(ID));
            final User user = getUser(req);
            Answer answer = new SuccessAnswer();
            final GoalMember member = goalDAO.getGoalMember(goal, user);
            System.out.println(member);
            if (member != null){
                goalDAO.removeMember(member);
                updater.remove(Subscribe.goal, goal.getId(), user);

                updater.update(goalDAO.getGoal(body.get(ID)));
            }
            write(resp, answer);
        }
    }
}
