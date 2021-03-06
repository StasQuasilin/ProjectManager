package utils.db.dao.category;

import entity.finance.category.Category;
import entity.finance.category.Header;
import entity.task.TaskStatistic;
import entity.user.User;

import java.util.List;

/**
 * Created by DELL on 10.07.2020.
 */
public abstract class CategoryDAO {
    public abstract List<Header> findCategory(String name, User user);
    public abstract Header getCategory(Object id);
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

    public abstract void removeCategories(User user);

    public abstract List<Header> getCategories(User owner, Header parent);

}
