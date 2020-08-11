package utils;

import entity.task.Task;
import entity.task.TaskStatus;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;

import java.util.List;

public class TaskUtil {

    private final TaskDAO taskDAO = daoService.getTaskDAO();

    public void checkPossibility(Task task){
        final List<Task> tasksByParent = taskDAO.getTasksByParent(task.getCategory());
        TaskStatus status = TaskStatus.done;
        for (Task t : tasksByParent) {
            if (t.getStatus() != TaskStatus.done) {
                status = TaskStatus.impossible;
                break;
            }
        }
        task.setStatus(status);
    }

    public void calculateSpendTime(Task task) {
    }
}