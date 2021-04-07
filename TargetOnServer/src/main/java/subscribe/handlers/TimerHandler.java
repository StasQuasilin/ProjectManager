package subscribe.handlers;

import entity.task.TimeLog;
import entity.user.User;
import subscribe.Subscribe;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;

public class TimerHandler extends SubscribeHandler {

    private final TaskDAO taskDAO = daoService.getTaskDAO();

    public TimerHandler() {
        super(Subscribe.timer);
    }

    @Override
    public Object getItems(User user) {
        final TimeLog log = taskDAO.getTimeLogByUser(user);
        if (log != null){
            return log.toJson();
        }
        return null;
    }
}
