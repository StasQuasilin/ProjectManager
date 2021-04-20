package utils.tree;

import entity.task.Task;
import entity.task.TaskStatus;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;

import java.util.LinkedList;

public class CalendarTaskUtil {

    private final TaskDAO taskDAO = daoService.getTaskDAO();

    public LinkedList<Task> getActiveParentItems(int parentId) {
        final LinkedList<Task> tasks = new LinkedList<>();
        for (Task task : taskDAO.getTasksByParent(parentId)) {
            if(task.getStatus() == TaskStatus.active && !task.isDoneIfChildren()){
                tasks.add(task);
            }
            tasks.addAll(getActiveParentItems(task.getHeader().getId()));
        }

        return tasks;
    }
}
