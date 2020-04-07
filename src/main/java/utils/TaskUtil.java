package utils;

import api.socket.UpdateUtil;
import entity.project.Project;
import entity.task.Task;
import entity.task.TaskStatus;
import entity.task.TaskStatistic;
import entity.task.TimeLog;
import entity.user.User;
import services.hibernate.HibernateSessionFactory;
import services.hibernate.Hibernator;
import services.hibernate.dbDAO;
import services.hibernate.dbDAOService;

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
            counts.put(status, counts.get(status) + task.getChildrenCount(status) + 1);
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
            if (size > 0){
                parent.setStatus(TaskStatus.folder);
            } else {
                parent.setStatus(TaskStatus.active);
            }
            dao.save(parent);
        }
        if (!parent.isTop()){
            checkParenthood(parent.getParent(), user);
        }
    }

    public static void main(String[] args) {
        Hibernator hibernator = Hibernator.getInstance();

        for (Task task : hibernator.query(Task.class, null)){
            checkParenthood(task, task.getOwner());
        }
        HibernateSessionFactory.shutdown();
    }

    public static void checkSpellTime(Task task) {
        List<TimeLog> logs = dao.getTimeLogs(task);
        int spend = 0;
        for (TimeLog log : logs){
            spend += log.getSpend();
        }
        TaskStatistic statistic = task.getStatistic();
        if (statistic == null){
            statistic = new TaskStatistic();
            statistic.setTask(task);
        }
        statistic.setSpendTime(spend);
        dao.save(statistic);
    }
}
