package utils.db.dao.goal;

import entity.finance.category.Category;
import entity.goal.Goal;
import entity.user.User;

import java.util.List;

public interface GoalDAO {
    List<Goal> getGoals(User user);
    Goal getGoal(Object id);
    void saveGoal(Goal goal);

    Goal getGoalByCategory(Category category);
}
