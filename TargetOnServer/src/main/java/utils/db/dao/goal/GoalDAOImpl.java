package utils.db.dao.goal;

import entity.goal.Goal;
import entity.goal.GoalMember;
import entity.user.User;
import utils.db.hibernate.Hibernator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static constants.Keys.*;

public class GoalDAOImpl implements GoalDAO{

    private final Hibernator hibernator = Hibernator.getInstance();

    @Override
    public List<Goal> getGoals(User user) {

        HashMap<String, Object> params = new HashMap<>();
        params.put(GOAL_OWNER, user);

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
        return null;
    }

    @Override
    public void saveGoal(Goal goal) {

    }
}
