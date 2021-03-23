package utils.finances;

import entity.finance.category.Category;
import entity.task.TaskStatistic;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;
import utils.db.hibernate.Hibernator;

import static constants.Keys.CATEGORY;

public class CategoryStatisticUtil {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final CategoryDAO categoryDAO = daoService.getCategoryDAO();
    private static final CategoryStatisticUtil instance = new CategoryStatisticUtil();
    public static CategoryStatisticUtil getInstance() {
        return instance;
    }

    public TaskStatistic getStatistic(Category category){
        return getStatistic(category, true);
    }

    private TaskStatistic getStatistic(Category category, boolean createIfNull){
        TaskStatistic statistic = hibernator.get(TaskStatistic.class, CATEGORY, category);
        return statistic;
    }

    public void updateStatistic(Category category) {
//        final Category parent = category.getParent();
//        if (parent != null){
//            final TaskStatistic statistic = getStatistic(parent);
//            statistic.clean();
//
//            for (Category child : categoryDAO.getChildren(parent)) {
//                final TaskStatistic childrenStat = getStatistic(child, false);
//                statistic.add(childrenStat);
//            }
//
//            hibernator.save(statistic);
//            updateStatistic(parent);
//        }
    }

    public void calculateChildren(Category category, TaskDAO taskDAO){
//        final Category parent = category.getParent();
//        if (parent != null){
//            final TaskStatistic statistic = getStatistic(parent);
//            statistic.setActiveChildren(0);
//            statistic.setProgressingChildren(0);
//            statistic.setDoneChildren(0);
//
//            for (Task task : taskDAO.getTasksByParent(parent)){
//                final Category children = task.getHeader();
//                TaskStatistic childrenStat = getStatistic(children, false);
//                statistic.add(task.getStatus(), childrenStat);
//            }
//            hibernator.save(statistic);
//            calculateChildren(parent, taskDAO);
//        }
    }
}
