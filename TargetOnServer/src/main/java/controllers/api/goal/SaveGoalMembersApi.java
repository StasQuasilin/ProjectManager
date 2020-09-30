package controllers.api.goal;

import constants.ApiLinks;
import constants.Keys;
import controllers.api.API;
import entity.goal.Goal;
import entity.goal.GoalMember;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;
import utils.db.dao.user.UserDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(ApiLinks.SAVE_GOAL_MEMBERS)
public class SaveGoalMembersApi extends API {

    private final GoalDAO goalDAO = daoService.getGoalDAO();
    private final UserDAO userDAO = daoService.getUserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            final Goal goal = goalDAO.getGoal(body.get(Keys.GOAL));
            final HashMap<Integer, GoalMember> memberHashMap = new HashMap<>();
            for (GoalMember member : goalDAO.getGoalMembers(goal)){
                memberHashMap.put(member.getId(), member);
            }
            for (Object o : (JSONArray)body.get(Keys.MEMBERS)){
                final int memberId = Integer.parseInt(String.valueOf(o));
                GoalMember goalMember = memberHashMap.remove(memberId);
                if(goalMember == null){
                    goalMember = new GoalMember();
                    goalMember.setGoal(goal);
                    goalMember.setMember(userDAO.getUserById(memberId));
                    goalDAO.saveMember(goalMember);
                }
            }
            for (GoalMember member : memberHashMap.values()){
                goalDAO.removeMember(member);
            }
            write(resp, SUCCESS_ANSWER);
        }
    }
}
