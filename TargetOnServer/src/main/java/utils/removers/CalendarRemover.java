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
    private final GoalDAO goalDAO = daoService.getGoalDAO();
    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final TransactionDAO transactionDAO = daoService.getTransactionDAO();
    private final Updater updater = new Updater();
    private final TaskSaver taskSaver = new TaskSaver();

    public void remove(CalendarItem item) {
        hibernator.remove(item);
        final Category category = item.getCategory();
        final Goal goal = goalDAO.getGoalByCategory(category);
        boolean removeCategory = true;
        if (goal != null){
            removeCategory = false;
        } else {
            final Task task = taskDAO.getTaskByCategory(category);
            if (task != null){
                if (task.getStatus() == TaskStatus.progressing) {
                    task.setStatus(TaskStatus.active);
                    taskSaver.save(task);
                }
                removeCategory = false;
            } else {
                final List<Transaction> transactions = transactionDAO.getTransactionsByCategory(category, 1);
                if (transactions.size() > 0){
                    removeCategory = false;
                }
            }
        }
        if (removeCategory){
            hibernator.remove(category);
        }
        updater.remove(Subscribe.calendar, item.getId(), item.getOwner());
    }
}
