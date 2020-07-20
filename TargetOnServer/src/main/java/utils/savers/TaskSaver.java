package utils.savers;

import entity.finance.category.Category;
import entity.task.Task;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;
import utils.finances.CategoryStatisticUtil;

public class TaskSaver {

    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final CategoryStatisticUtil categoryStatisticUtil = new CategoryStatisticUtil();
    public void save(Task task){
        taskDAO.saveTask(task);
        Category category = task.getCategory();
        categoryStatisticUtil.updateStatistic(category);
        categoryStatisticUtil.updateChildren(category, taskDAO);
    }
}
