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

    private final JSONArray array = new JSONArray();
    public CalendarHandler() {
        super(Subscribe.calendar);
    }

    @Override
    public Object getItems(User user) {
        return array;
    }


}
