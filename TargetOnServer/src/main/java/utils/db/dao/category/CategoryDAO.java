package utils.db.dao.category;

import entity.finance.Category;
import entity.user.User;

import java.util.List;

/**
 * Created by DELL on 10.07.2020.
 */
public abstract class CategoryDAO {
    abstract List<Category> findCategory(String name, User user);
    public abstract Category getCategory(Object id);
    public void saveCategory(Category category){
        save(category);
    }
    abstract void save(Category category);
}
