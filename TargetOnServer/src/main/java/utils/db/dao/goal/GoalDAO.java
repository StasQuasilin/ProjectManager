package utils.db.dao.goal;

import entity.goal.Goal;
import entity.goal.GoalMember;
import entity.user.User;

import java.util.List;

public interface GoalDAO {
    List<Goal> getGoals(User user);
    Goal getGoal(Object id);
    void saveGoal(Goal goal);
    Goal getGoalByCategory(int title);
    void removeGoals(User user);

    List<GoalMember> getGoalMembers(Goal goal);

    void saveMember(GoalMember goalMember);

    void removeMember(GoalMember member);
}
