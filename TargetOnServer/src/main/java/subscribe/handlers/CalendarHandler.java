package subscribe.handlers;

import entity.task.Task;
import entity.task.TaskStatus;
import entity.user.User;
import org.json.simple.JSONArray;
import subscribe.Subscribe;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;

public class CalendarHandler extends SubscribeHandler {



    public CalendarHandler() {
        super(Subscribe.calendar);
    }

    @Override
    Object getItems(User user) {
        return null;
    }
}
