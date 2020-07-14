package utils.db.dao.category;

import entity.finance.Category;
import entity.task.TaskStatistic;
import entity.user.User;
import utils.db.hibernate.Hibernator;

import static constants.Keys.*;

import java.util.List;

public class CategoryDAOImpl extends CategoryDAO {

    private final Hibernator hibernator = Hibernator.getInstance();

    @Override
    List<Category> findCategory(String name, User user) {
        return null;
    }

    @Override
    public Category getCategory(Object id) {
        return hibernator.get(Category.class, ID, id);
    }

    @Override
    void save(Category category) {
        hibernator.save(category);
    }

    @Override
    public List<Category> getChildren(Category parent) {
        return hibernator.query(Category.class, PARENT, parent);
    }

    @Override
    public TaskStatistic getStatistic(Category category) {
        return null;
    }
}
