package subscribe;

import entity.goal.Goal;
import entity.task.Task;
import entity.user.User;
import org.json.simple.JSONArray;
import subscribe.handlers.SubscribeHandler;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;
import utils.tree.CalendarTaskUtil;

public class CalendarTaskHandler extends SubscribeHandler {

    private final GoalDAO goalDAO = daoService.getGoalDAO();
    private final CalendarTaskUtil calendarTaskUtil = new CalendarTaskUtil();

    protected CalendarTaskHandler() {
        super(Subscribe.calendar_tasks);
    }

    @Override
    public Object getItems(User user) {
        JSONArray array = new JSONArray();
        for(Goal goal : goalDAO.getGoals(user)){
            for (Task task : calendarTaskUtil.getActiveParentItems(goal.getTitle().getId())){
                array.add(task.toJson());
            }
        }
        return array;
    }


}
