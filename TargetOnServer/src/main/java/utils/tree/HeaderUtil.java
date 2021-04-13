package utils.tree;

import entity.calendar.CalendarItem;
import entity.finance.buy.BuyListItem;
import entity.finance.category.Header;
import entity.goal.Goal;
import entity.task.Task;
import entity.task.TaskStatistic;
import utils.db.dao.TitleDAO;
import utils.db.dao.calendar.CalendarDAO;
import utils.db.dao.daoService;
import utils.db.dao.finance.buy.BuyListDAO;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.db.dao.goal.GoalDAO;
import utils.db.dao.tree.TaskDAO;
import utils.savers.TransactionDetailUtil;

public class HeaderUtil {

    private final CalendarDAO calendarDAO = daoService.getCalendarDAO();
    private final BuyListDAO buyListDAO = daoService.getBuyListDAO();
    private final GoalDAO goalDAO = daoService.getGoalDAO();
    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final TitleDAO titleDAO = daoService.getTitleDAO();

    public void checkHeader(Header header) {
        //todo CalendarItem
        final CalendarItem item = calendarDAO.getCalendarItemByCategory(header);
        if(item != null){
            return;
        }
        //todo BuyListItem
        final BuyListItem buyListItem = buyListDAO.getItemByHeader(header);
        if(buyListItem != null){
            return;
        }
        //todo goal
        final Goal goal = goalDAO.getGoalByHeader(header.getId());
        if(goal != null){
            return;
        }
        //todo Task
        final Task task = taskDAO.getTaskByHeader(header);
        if(task != null){
            return;
        }


        //todo CategoryAccount?
        //todo CategoryStatistic?
        //todo CategoryFile?
        //todo ActiveGoal


        //todo TaskSettings
        //todo TaskStatistic
        //todo TimeLog

        titleDAO.removeTitle(header);
    }
}
