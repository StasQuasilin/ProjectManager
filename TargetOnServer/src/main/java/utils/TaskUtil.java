package utils;

import entity.task.Task;
import entity.task.TaskStatus;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;

import java.util.List;

public class TaskUtil {

    private final TaskDAO taskDAO = daoService.getTaskDAO();

    public void checkPossibility(Task task){
//        final List<Task> tasksByParent = taskDAO.getTasksByParent(task.getHeader());

//        TaskStatus status = tasksByParent.size() == 0 ? TaskStatus.active : task.isDoneIfChildren() ? TaskStatus.done : TaskStatus.active;
//
//        for (Task t : tasksByParent) {
//            if (t.getStatus() != TaskStatus.done) {
//                status = TaskStatus.impossible;
//                break;
//            }
//        }
//        task.setStatus(status);
    }

    public void calculateSpendTime(Task task) {
    }
}
