package utils.db.dao.tree;

import entity.finance.category.Category;
import entity.task.Task;
import entity.task.TaskStatus;
import entity.user.User;
import subscribe.Subscribe;
import utils.CategoryUtil;
import utils.Updater;
import utils.db.hibernate.Hibernator;
import utils.finances.CategoryStatisticUtil;

import java.util.HashMap;
import java.util.List;

import static constants.Keys.*;

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
        final Category category = task.getCategory();
        hibernator.save(category);
        hibernator.save(task);
        updater.update(Subscribe.tree, task, task.getOwner());
    }

    @Override
    public List<Task> getTaskByStatus(User user, TaskStatus status) {
        HashMap<String,Object> param = new HashMap<>();
        param.put(TASK_OWNER, user);
        param.put(STATUS, status);
        return hibernator.query(Task.class, param);
    }

    @Override
    public Task getTaskByCategory(Category category) {
        return hibernator.get(Task.class, CATEGORY, category);
    }
}
