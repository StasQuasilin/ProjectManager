package utils.db.dao.category;

import entity.finance.transactions.Category;
import entity.task.TaskStatistic;
import entity.user.User;

import java.util.List;

/**
 * Created by DELL on 10.07.2020.
 */
public abstract class CategoryDAO {
    public abstract List<Category> findCategory(String name, User user);
    public abstract Category getCategory(Object id);
    public void saveCategory(Category category){
        save(category);
    }
    abstract void save(Category category);
    public abstract List<Category> getChildren(Category parent);
    public abstract TaskStatistic getStatistic(Category category);
    public void updateStatistic(Category category) {
        TaskStatistic statistic = getStatistic(category);
        if (statistic == null){
            statistic = new TaskStatistic();
        }

    }
}
