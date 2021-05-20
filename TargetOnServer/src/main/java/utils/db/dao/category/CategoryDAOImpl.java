package utils.db.dao.category;

import entity.finance.category.Category;
import entity.finance.category.CategoryStatistic;
import entity.finance.category.Header;
import entity.finance.category.HeaderType;
import entity.task.TaskStatistic;
import entity.user.User;
import utils.db.hibernate.DateContainers.OR;
import utils.db.hibernate.Hibernator;

import static constants.Keys.*;

import java.util.HashMap;
import java.util.List;

public class CategoryDAOImpl extends CategoryDAO {

    private final Hibernator hibernator = Hibernator.getInstance();

    @Override
    public List<Header> findCategory(String name, User user, Header parent) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put(OWNER, user);
        params.put(TYPE, new OR(HeaderType.task, HeaderType.category));
        if (parent != null){
            params.put(PARENT, parent);
        }

        return hibernator.find(Header.class, params, VALUE, name);
    }

    @Override
    public Header getCategory(Object id) {
        return hibernator.get(Header.class, ID, id);
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
    public TaskStatistic getStatistic(Header header) {
        return hibernator.get(TaskStatistic.class, HEADER, header);
    }

    @Override
    public void removeCategories(User user) {
        hibernator.remove(Category.class, "owner", user);
    }

    @Override
    public List<Header> getCategories(User owner, Header parent) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("owner", owner);
        params.put("parent", parent);
        params.put("hidden", false);
        return hibernator.query(Header.class, params);
    }

    @Override
    public List<Header> getUserCategory(User user) {
        return hibernator.query(Header.class, OWNER, user);
    }

    @Override
    public CategoryStatistic getCategoryStatistic(Header header) {
        return hibernator.get(CategoryStatistic.class, HEADER, header);
    }
}
