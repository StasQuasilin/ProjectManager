package subscribe.handlers;

import entity.task.Task;
import entity.user.User;
import subscribe.Subscribe;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;

public class TreeHandler extends SubscribeHandler{

    private final TaskDAO taskDAO = daoService.getTaskDAO();

    public TreeHandler() {
        super(Subscribe.tree);
    }

    @Override
    Object getItems(User user) {
        return null;
    }
}
