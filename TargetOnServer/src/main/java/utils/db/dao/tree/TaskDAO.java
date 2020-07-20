package utils.db.dao.tree;

import entity.finance.category.Category;
import entity.task.Task;
import entity.task.TaskStatus;
import entity.user.User;

import java.util.List;

public interface TaskDAO {
    List<Task> getTasksByParent(Category parent);
    List<Task> getTaskByOwner(User owner);
    Task getTask(Object id);
    void saveTask(Task task);
    List<Task> getTaskByStatus(User user, TaskStatus active);
    Task getTaskByCategory(Category category);
}
