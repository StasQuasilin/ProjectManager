package utils.removers;

import entity.goal.Goal;
import subscribe.Subscribe;
import utils.Updater;
import utils.db.hibernate.Hibernator;

public class GoalRemover {

    private final CategoryRemover categoryRemover = new CategoryRemover();
    private final Updater updater = new Updater();

    public void remove(Goal goal) {
        categoryRemover.removeGoal(goal);
        updater.remove(Subscribe.goal, goal.getId(), goal.getOwner());
    }
}
