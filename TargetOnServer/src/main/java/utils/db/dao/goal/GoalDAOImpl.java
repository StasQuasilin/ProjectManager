package utils.db.dao.goal;

import entity.goal.Goal;
import entity.goal.GoalMember;
import entity.user.User;
import subscribe.Subscribe;
import utils.Updater;
import utils.db.hibernate.Hibernator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static constants.Keys.*;

public class GoalDAOImpl implements GoalDAO{


    private final Hibernator hibernator = Hibernator.getInstance();
    private final Updater updater = new Updater();

    @Override
    public List<Goal> getGoals(User user) {
        System.out.println("get goals for " + user);
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
        hibernator.save(goal.getCategory());
        hibernator.save(goal);
        updater.update(Subscribe.goal, goal, goal.getOwner());
    }
}
