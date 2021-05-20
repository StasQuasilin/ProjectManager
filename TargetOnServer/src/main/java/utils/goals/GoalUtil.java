package utils.goals;

import entity.finance.category.Header;
import entity.goal.Goal;
import entity.goal.GoalMember;
import entity.goal.GoalStatus;
import entity.task.Task;
import entity.task.TaskStatus;
import subscribe.Subscribe;
import utils.Updater;
import utils.db.dao.TitleDAO;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;
import utils.db.dao.tree.TaskDAO;

public class GoalUtil {

    private final GoalDAO goalDAO = daoService.getGoalDAO();
    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final Updater updater = new Updater();
    public void checkGoalStatus(Task task) {
        final Header header = task.getHeader();
        Header p = header.getParent();
        Header parent = p;
        while (p != null ){
            p = p.getParent();
        }
        final Goal goal = goalDAO.getGoalByHeader(parent.getId());

        boolean save = false;
        if (calculateChildren(parent)){
            if (goal.getStatus() != GoalStatus.done){
                goal.setStatus(GoalStatus.done);
                save = true;
            }
        } else if (goal.getStatus() == GoalStatus.done){
            goal.setStatus(GoalStatus.active);
            save = true;
        }
        if (save){
            goalDAO.saveGoal(goal);
            updater.update(Subscribe.goal, goal, goal.getOwner());
            for (GoalMember member : goalDAO.getGoalMembers(goal)){
                updater.update(Subscribe.goal, goal, member.getMember());
            }
        }
    }

    private boolean calculateChildren(Header parent) {
        int done = 0;
        int all = 0;
        for (Task t : taskDAO.getTasksByParent(parent)){
            all++;
            if (t.getStatus() == TaskStatus.done){
                done++;
            }
            if (!calculateChildren(t.getHeader())){
                return false;
            }
        }
        return all == done;
    }


}
