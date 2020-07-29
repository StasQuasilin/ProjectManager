package utils.db.dao.goal;

import entity.finance.category.Category;
import entity.goal.Goal;
import entity.user.User;
import subscribe.Subscribe;
import utils.Updater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by DELL on 10.07.2020.
 */
public class GaolDAOPlug implements GoalDAO {

    private final Updater updater = new Updater();
    private static final HashMap<Integer, Goal> goals = new HashMap<>();
    static {
        for (int i = 0; i < 8; i++){
            save(createGoal());
        }
    }
    static int count = 0;
    private static Goal createGoal() {
        Category category = new Category();
        category.setId(count);
        category.setTitle("Goal #" + ++count);
        Goal goal = new Goal();
        goal.setCategory(category);
        return goal;
    }

    @Override
    public List<Goal> getGoals(User user) {
        return new ArrayList<>(goals.values());
    }

    @Override
    public Goal getGoal(Object id) {
        if (id != null){
            int integer = Integer.parseInt(String.valueOf(id));
            return goals.get(integer);
        }
        return null;
    }

    @Override
    public void saveGoal(Goal goal) {
        save(goal);
        updater.update(Subscribe.goal, goal, goal.getOwner());
    }

    @Override
    public Goal getGoalByCategory(Category category) {
        return null;
    }

    static void save(Goal goal){
        if (goal.getId() <= 0){
            goal.setId(goals.size() + 1);
        }
        goals.put(goal.getId(), goal);
    }
}
