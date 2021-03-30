package subscribe.handlers;

import entity.task.Task;
import entity.task.TaskStatus;
import entity.user.User;
import org.json.simple.JSONArray;
import subscribe.Subscribe;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;

public class CalendarHandler extends SubscribeHandler {

    private final TaskDAO taskDAO = daoService.getTaskDAO();

    public CalendarHandler() {
        super(Subscribe.calendar);
    }

    @Override
    Object getItems(User user) {
        JSONArray array = new JSONArray();
        for (Task task : taskDAO.getTaskByStatus(user, TaskStatus.active)){
            array.add(task.toJson());
        }
        return array;
    }
}
