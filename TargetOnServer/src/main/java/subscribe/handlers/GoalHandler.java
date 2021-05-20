package subscribe.handlers;

import constants.Keys;
import entity.finance.category.TitleCost;
import entity.goal.ActiveGoal;
import entity.goal.Goal;
import entity.task.TaskStatistic;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import subscribe.Subscribe;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;
import utils.db.dao.goal.GoalDAOImpl;
import utils.db.dao.tree.TaskDAO;
import utils.db.hibernate.Hibernator;

import static constants.Keys.TITLE;

public class GoalHandler extends SubscribeHandler{

    private final GoalDAO goalDAO = daoService.getGoalDAO();
    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final Hibernator hibernator = Hibernator.getInstance();

    public GoalHandler() {
        super(Subscribe.goal);
    }

    @Override
    public Object getItems(User user) {
        JSONArray array = new JSONArray();
        final ActiveGoal activeGoal = goalDAO.getActiveGoal(user);
        for (Goal goal : goalDAO.getGoals(user)){
            goal.setCost(hibernator.get(TitleCost.class, TITLE,goal.getTitle()));
            if(activeGoal != null) {
                goal.setActive(goal.getTitle().getId() == activeGoal.getHeader().getId());
            }

            final JSONObject object = goal.toJson();
            final TaskStatistic statistic = taskDAO.getStatistic(goal.getTitle().getId());
            if (statistic != null){
                object.put(Keys.STATISTIC, statistic.toJson());
            }
            array.add(object);
        }
        return array;
    }
}
