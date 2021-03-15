package utils.savers;

import entity.task.Task;
import utils.Updater;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;
import utils.finances.CategoryStatisticUtil;

public class TaskSaver {

    private final Updater updater = new Updater();
    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final CategoryStatisticUtil categoryStatisticUtil = new CategoryStatisticUtil();

    public void save(Task task){
        taskDAO.saveTask(task);
//        final Header category = task.getHeader();
//        categoryStatisticUtil.calculateChildren(category, taskDAO);
//        categoryStatisticUtil.updateStatistic(category);
//        updater.update(Subscribe.tree, task, task.getOwner());
    }
}
