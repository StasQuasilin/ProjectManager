package utils;

import api.socket.UpdateUtil;
import entity.calendar.CalendarItem;
import entity.project.Task;
import entity.project.TaskStatus;
import entity.task.TaskStatistic;
import entity.user.User;
import services.State;
import services.hibernate.dbDAO;
import services.hibernate.dbDAOService;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class TaskUtil {

    static dbDAO dao = dbDAOService.getDao();
    static UpdateUtil updateUtil = new UpdateUtil();

    public static void checkParenthood(Task parent, User user) {
        List<Task> tasks = dao.getTaskByUserAndParent(user, parent);
        HashMap<TaskStatus, Integer> counts = new HashMap<>();

        for (Task task : tasks){
            TaskStatus status = task.getStatus();
            if (!counts.containsKey(status)){
                counts.put(status, 0);
            }
            counts.put(status, counts.get(status) + 1);
        }

        TaskStatistic statistic = dao.getTaskStatistic(parent);
        if (statistic == null){
            statistic = new TaskStatistic();
            statistic.setTask(parent);
        }

        if (counts.containsKey(TaskStatus.active))
        statistic.setActiveChildren(counts.get(TaskStatus.active));
        if (counts.containsKey(TaskStatus.progressing))
        statistic.setProgressingChildren(counts.get(TaskStatus.progressing));
        if (counts.containsKey(TaskStatus.done))
        statistic.setDoneChildren(counts.get(TaskStatus.done));
        dao.save(statistic);

        int size = tasks.size();
        if (parent.getChildren() != size){
            parent.setChildren(size);
            dao.save(parent);
        }
        if (!parent.isTop()){
            checkParenthood(parent.getParent(), user);
        }
    }

    public static void main(String[] args) {
        Date date = Date.valueOf(LocalDate.now());
        for (CalendarItem calendarItem : dao.getCalendarItems(date)){
            System.out.println(calendarItem.getTask().getTitle());
        }
    }
}
