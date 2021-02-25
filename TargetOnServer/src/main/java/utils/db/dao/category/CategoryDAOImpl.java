package utils.db.dao.category;

import entity.finance.category.Category;
import entity.finance.category.Header;
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
        params.put(CATEGORY_OWNER, user);
        params.put(HIDDEN, false);

        return hibernator.find(Category.class, params, HEADER_VALUE, name);
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

    @Override
    public void removeCategories(User user) {
        hibernator.remove(Category.class, "owner", user);
    }

    @Override
    public List<Category> getCategories(User owner, Category parent) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("owner", owner);
        params.put("parent", parent);
        params.put("hidden", false);
        return hibernator.query(Category.class, params);
    }

    @Override
    public List<Category> getCategoryByHeader(Header header, int limit) {
        return hibernator.query(Category.class, HEADER, header, limit);
    }
}
