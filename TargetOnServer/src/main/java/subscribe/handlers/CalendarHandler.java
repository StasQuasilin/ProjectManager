package subscribe.handlers;

import entity.goal.Goal;
import entity.task.Task;
import entity.task.TaskStatus;
import entity.user.User;
import org.json.simple.JSONArray;
import subscribe.Subscribe;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;
import utils.db.dao.tree.TaskDAO;

public class CalendarHandler extends SubscribeHandler {

    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final GoalDAO goalDAO = daoService.getGoalDAO();

    public CalendarHandler() {
        super(Subscribe.calendar);
    }

    @Override
    Object getItems(User user) {
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
