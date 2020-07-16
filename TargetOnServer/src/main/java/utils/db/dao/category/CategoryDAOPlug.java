package utils.db.dao.category;

import entity.finance.transactions.Category;
import entity.task.TaskStatistic;
import entity.user.User;

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
    public List<Category> findCategory(String name, User user) {
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

    @Override
    public List<Category> getChildren(Category parent) {
        return null;
    }

    @Override
    public TaskStatistic getStatistic(Category category) {
        return null;
    }
}
