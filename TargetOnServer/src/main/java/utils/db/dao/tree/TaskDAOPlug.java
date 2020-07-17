package utils.db.dao.tree;

import entity.finance.category.Category;
import entity.task.Task;
import entity.task.TaskStatus;
import entity.user.User;
import subscribe.Subscribe;
import utils.Updater;

import java.util.List;

public class TaskDAOPlug implements TaskDAO {

    private final Updater updater = new Updater();

    @Override
    public List<Task> getTasksByParent(Category parent) {
        return null;
    }

    @Override
    public List<Task> getTaskByOwner(User owner) {
        return null;
    }

    @Override
    public Task getTask(Object id) {
        return null;
    }

    @Override
    public void saveTask(Task task) {
        updater.update(Subscribe.tree, task, task.getOwner());
    }

    @Override
    public List<Task> getTaskByStatus(User user, TaskStatus active) {
        return null;
    }
}
