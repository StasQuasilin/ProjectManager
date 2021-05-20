package utils.db.dao.goal;

import entity.finance.category.Header;
import entity.goal.ActiveGoal;
import entity.goal.Goal;
import entity.goal.GoalMember;
import entity.user.User;

import java.util.List;

public interface GoalDAO {
    List<Goal> getGoals(User user);
    Goal getGoal(Object id);
    void saveGoal(Goal goal);
    Goal getGoalByHeader(int title);
    void removeGoals(User user);

    List<GoalMember> getGoalMembers(Goal goal);

    void saveMember(GoalMember goalMember);

    void removeMember(GoalMember member);

    void changeActiveGoal(Header header, User user);

    ActiveGoal getActiveGoal(User user);

    void update(Goal goal);

    GoalMember getGoalMember(Goal goal, User user);
}
