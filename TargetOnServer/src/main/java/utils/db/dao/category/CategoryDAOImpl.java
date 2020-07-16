package utils.db.dao.category;

import entity.finance.transactions.Category;
import entity.task.TaskStatistic;
import entity.user.User;
import utils.db.hibernate.Hibernator;

import static constants.Keys.*;

import java.util.HashMap;
import java.util.List;

public class CategoryDAOImpl extends CategoryDAO {

    private final Hibernator hibernator = Hibernator.getInstance();

    @Override
    public List<Category> findCategory(String name, User user) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put(OWNER, user);
        params.put(HIDDEN, false);

        return hibernator.find(Category.class, params, TITLE, name);
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
