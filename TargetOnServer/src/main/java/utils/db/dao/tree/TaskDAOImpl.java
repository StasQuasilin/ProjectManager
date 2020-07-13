package utils.db.dao.tree;

import entity.task.Task;
import entity.user.User;

import java.util.List;

public class TaskDAOImpl implements TaskDAO {
    @Override
    public List<Task> getTasksByParent(Task task) {
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

    }
}
