package utils.db.dao.category;

import entity.finance.Category;
import entity.goal.Goal;
import entity.user.User;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;

import java.util.HashMap;
import java.util.List;

public class CategoryDAOPlug extends CategoryDAO {

    private static final HashMap<Integer, Category> categoryMap = new HashMap<>();
    static {
//        final GoalDAO goalDAO = daoService.getGoalDAO();
//        for (Goal goal : goalDAO.getGoals(null)){
//            final Category c = goal.getCategory();
//            categoryMap.put(c.getId(), c);
//        }
    }

    @Override
    List<Category> findCategory(String name, User user) {
        return null;
    }

    @Override
    public Category getCategory(Object id) {
        if (id != null){
            int integer = Integer.parseInt(String.valueOf(id));
            return categoryMap.getOrDefault(integer, null);
        }
        return null;
    }

    @Override
    void save(Category category) {

    }
}
