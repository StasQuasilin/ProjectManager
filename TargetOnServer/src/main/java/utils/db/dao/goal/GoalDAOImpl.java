package utils.db.dao.goal;

import entity.Title;
import entity.finance.category.Header;
import entity.goal.ActiveGoal;
import entity.goal.Goal;
import entity.goal.GoalMember;
import entity.task.TaskStatistic;
import entity.user.User;
import subscribe.Subscribe;
import utils.Updater;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;
import utils.db.hibernate.Hibernator;

import java.util.HashMap;
import java.util.List;

import static constants.Keys.*;

public class GoalDAOImpl implements GoalDAO{


    private final Hibernator hibernator = Hibernator.getInstance();
    private final Updater updater = new Updater();
    private final TaskDAO taskDAO = daoService.getTaskDAO();

    @Override
    public List<Goal> getGoals(User user) {

        HashMap<String, Object> params = new HashMap<>();
        params.put(TITLE_OWNER, user);
        //My own goals
        List<Goal> goals = hibernator.query(Goal.class, params);
        params.clear();
        params.put(MEMBER, user);
        //Goals where i am a member
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
        hibernator.save(goal.getTitle());
        hibernator.save(goal);
        goal.setStatistic(taskDAO.getStatistic(goal.getTitle().getId()));
        update(goal);
    }

    @Override
    public void update(Goal goal){
        updater.update(Subscribe.goal, goal, goal.getOwner());
    }

    @Override
    public Goal getGoalByHeader(int title) {
        return hibernator.get(Goal.class, TITLE, title);
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

    @Override
    public void changeActiveGoal(Header header, User user) {
        ActiveGoal activeGoal = getActiveGoal(user);
        if (activeGoal == null){
            activeGoal = new ActiveGoal();
            activeGoal.setUser(user);
        }
        activeGoal.setHeader(header);
        hibernator.save(activeGoal);
    }

    @Override
    public ActiveGoal getActiveGoal(User user) {
        return hibernator.get(ActiveGoal.class, USER, user);
    }
}
