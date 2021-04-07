package subscribe;

import entity.goal.Goal;
import entity.task.Task;
import entity.task.TaskStatus;
import entity.user.User;
import org.json.simple.JSONArray;
import subscribe.handlers.SubscribeHandler;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;
import utils.db.dao.tree.TaskDAO;

public class CalendarTaskHandler extends SubscribeHandler {

    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final GoalDAO goalDAO = daoService.getGoalDAO();

    protected CalendarTaskHandler() {
        super(Subscribe.calendar_tasks);
    }

    @Override
    public Object getItems(User user) {
        JSONArray array = new JSONArray();
        for(Goal goal : goalDAO.getGoals(user)){
            addItems(goal.getTitle().getId(), array);
        }
        return array;
    }

    private void addItems(int headerId, JSONArray array) {
        for (Task task : taskDAO.getTasksByParent(headerId)){
            if(task.getStatus() == TaskStatus.active && !task.isDoneIfChildren()){
                array.add(task.toJson());
            }
            addItems(task.getHeader().getId(), array);
        }
    }
}
