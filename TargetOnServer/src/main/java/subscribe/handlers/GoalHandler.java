package subscribe.handlers;

import entity.goal.Goal;
import entity.user.User;
import org.json.simple.JSONArray;
import subscribe.Subscribe;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;
import utils.db.dao.goal.GoalDAOImpl;

public class GoalHandler extends SubscribeHandler{

    private final GoalDAO goalDAO = daoService.getGoalDAO();

    public GoalHandler() {
        super(Subscribe.goal);
    }

    @Override
    Object getItems(User user) {
        JSONArray array = new JSONArray();
        for (Goal goal : goalDAO.getGoals(user)){
            array.add(goal.toJson());
        }
        return array;
    }
}
