package utils.removers;

import entity.calendar.CalendarItem;
import entity.finance.category.Category;
import entity.finance.transactions.Transaction;
import entity.goal.Goal;
import entity.task.Task;
import entity.task.TaskStatus;
import subscribe.Subscribe;
import utils.Updater;
import utils.db.dao.daoService;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.db.dao.goal.GoalDAO;
import utils.db.dao.tree.TaskDAO;
import utils.db.hibernate.Hibernator;
import utils.savers.TaskSaver;

import java.util.List;

public class CalendarRemover {

    private final Hibernator hibernator = Hibernator.getInstance();

    private final CategoryRemover categoryRemover = new CategoryRemover();
    private final Updater updater = new Updater();

    public void remove(CalendarItem item) {
        hibernator.remove(item);
        categoryRemover.remove(item.getCategory());

//        updater.remove(Subscribe.calendar, item.getId(), item.getOwner());
    }
}
