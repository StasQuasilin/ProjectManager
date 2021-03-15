package utils.removers;

import entity.calendar.CalendarItem;
import entity.finance.category.Category;
import entity.finance.transactions.Transaction;
import entity.goal.Goal;
import entity.task.Task;
import utils.db.dao.calendar.CalendarDAO;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.daoService;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.db.dao.goal.GoalDAO;
import utils.db.dao.tree.TaskDAO;
import utils.db.hibernate.Hibernator;
import utils.savers.TaskSaver;

public class CategoryRemover {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final GoalDAO goalDAO = daoService.getGoalDAO();
    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final CalendarDAO calendarDAO = daoService.getCalendarDAO();
    private final TransactionDAO transactionDAO = daoService.getTransactionDAO();
    private final TaskSaver taskSaver = new TaskSaver();
    private final CategoryDAO categoryDAO = daoService.getCategoryDAO();

    public boolean remove(Category category){
        boolean removeCategory = true;
//        System.out.println("Try remove category '" + category.getTitle() + "'");
//        for (Category child : categoryDAO.getChildren(category)){
//            if (!remove(child)){
//                removeCategory = false;
//                System.out.println("\tCategory locked of children '" + child.getTitle() + "'");
//            }
//        }
//        if (removeCategory) {
//            final Goal goal = goalDAO.getGoalByCategory(category);
//            if (goal != null) {
//                removeCategory = false;
//                System.out.println("\tCategory locked of goal '" + goal.getId() + "'");
//            } else {
//                final Task task = taskDAO.getTaskByCategory(category);
//                if (task != null) {
//                    if (task.getStatus() == TaskStatus.progressing) {
//                        task.setStatus(TaskStatus.active);
//                        taskSaver.save(task);
//                    }
//                    removeCategory = false;
//                    System.out.println("\tCategory locked of task " + task.getId());
//                } else {
//                    final List<Transaction> transactions = transactionDAO.getTransactionsByCategory(category, 1);
//                    if (transactions.size() > 0) {
//                        removeCategory = false;
//                        System.out.println("\tCategory locked " + transactions.size() + " transactions");
//                    }
//                }
//            }
//            if (removeCategory) {
//                System.out.println("\tRemove success");
//                hibernator.remove(category);
//            }
//        }
        return removeCategory;
    }
    public void removeGoal(Goal goal){
        hibernator.remove(goal);
//        for (Task child : taskDAO.getTasksByParent(goal.getHeader())){
//            removeTask(child);
//        }
    }
    public void removeTask(Task task){
        hibernator.remove(task);
//        for (Task child : taskDAO.getTasksByParent(task.getHeader())){
//            removeTask(child);
//        }
    }
    public void removeCalendar(CalendarItem item){

    }
    public void  removeCalendar(Category category){
//        final CalendarItem item = calendarDAO.getCalendarItemByCategory(category.getId());
//        if (item != null){
//            removeCalendar(item);
//        }
    }
    public void removeTransaction(Transaction transaction){

    }
    //goal
    //task
    //calendar
    //transaction
}
