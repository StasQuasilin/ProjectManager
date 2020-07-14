package utils.db.dao.tree;

import entity.finance.Category;
import entity.task.Task;
import entity.user.User;
import subscribe.Subscribe;
import utils.Updater;
import utils.db.hibernate.Hibernator;

import java.util.HashMap;
import java.util.List;
import static constants.Keys.ID;

public class TaskDAOImpl implements TaskDAO {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final Updater updater = new Updater();

    @Override
    public List<Task> getTasksByParent(Category parent) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("category/parent", parent);
        return hibernator.query(Task.class, params);
    }

    @Override
    public List<Task> getTaskByOwner(User owner) {
        return null;
    }

    @Override
    public Task getTask(Object id) {
        return hibernator.get(Task.class, ID, id);
    }

    @Override
    public void saveTask(Task task) {
        hibernator.save(task.getCategory());
        hibernator.save(task);
        updater.update(Subscribe.tree, task, task.getOwner());
    }
}
