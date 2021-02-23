package utils.db.dao.goal;

import entity.finance.category.Category;
import entity.goal.Goal;
import entity.goal.GoalMember;
import entity.user.User;
import subscribe.Subscribe;
import utils.Updater;
import utils.db.hibernate.Hibernator;

import java.util.HashMap;
import java.util.List;

import static constants.Keys.*;

public class GoalDAOImpl implements GoalDAO{


    private final Hibernator hibernator = Hibernator.getInstance();
    private final Updater updater = new Updater();

    @Override
    public List<Goal> getGoals(User user) {
        HashMap<String, Object> params = new HashMap<>();
        params.put(TASK_OWNER, user);

        List<Goal> goals = hibernator.query(Goal.class, params);

        params.clear();
        params.put(MEMBER, user);
        for (GoalMember member : hibernator.query(GoalMember.class, params)){
            goals.add(member.getGoal());
        }
        return goals;
    }

    @Override
    public Goal getGoal(Object id) {
        return hibernator.get(Goal.class, ID, id);
    }

    @Override
    public void saveGoal(Goal goal) {
        hibernator.save(goal.getHeader());
        hibernator.save(goal);
        updater.update(Subscribe.goal, goal, goal.getOwner());
    }

    @Override
    public Goal getGoalByCategory(Category category) {
        return hibernator.get(Goal.class, CATEGORY, category);
    }

    @Override
    public void removeGoals(User user) {
        hibernator.remove(Goal.class, "category/owner", user);
    }

    @Override
    public List<GoalMember> getGoalMembers(Goal goal) {
        return hibernator.query(GoalMember.class, "goal", goal);
    }

    @Override
    public void saveMember(GoalMember goalMember) {
        hibernator.save(goalMember);
    }

    @Override
    public void removeMember(GoalMember member) {
        hibernator.remove(member);
    }
}
