package utils.savers;

import entity.calendar.CalendarItem;
import entity.calendar.ExecutionStatus;
import entity.task.Task;
import entity.task.TaskStatus;
import utils.Updater;
import utils.db.dao.calendar.CalendarDAO;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;

public class CalendarSaver {

    private final CalendarDAO calendarDAO = daoService.getCalendarDAO();
    private final TaskDAO taskDAO =daoService.getTaskDAO();
    private final Updater updater = new Updater();
    public void save(CalendarItem item){
        calendarDAO.saveCalendarItem(item);
//        updater.update(Subscribe.calendar, item, item.getOwner());
//        final Task task = taskDAO.getTaskByHeader(item.getCategory());
//        if (task != null){
//            final ExecutionStatus status = item.getStatus();
//            if (status == ExecutionStatus.done){
//                task.setStatus(TaskStatus.done);
//            }
//        }
    }
}
