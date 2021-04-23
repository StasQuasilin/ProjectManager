package utils.db.dao.tree;

import entity.finance.category.Header;
import entity.task.*;
import entity.user.User;
import subscribe.Subscribe;
import utils.Updater;
import utils.db.hibernate.DateContainers.OR;
import utils.db.hibernate.Hibernator;

import java.util.HashMap;
import java.util.List;

import static constants.Keys.*;

public class TaskDAOImpl implements TaskDAO {

    private final Hibernator hibernator = Hibernator.getInstance();

    @Override
    public List<Task> getTasksByParent(Object parent) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("header/parent", parent);
        return hibernator.query(Task.class, params);
    }

    @Override
    public List<Task> getTaskByOwner(User owner) {
        return null;
    }

    @Override
    public Task getTask(Object id) {
        return hibernator.get(Task.class, ID, id);
    }

    @Override
    public void saveTask(Task task) {
        final Header header = task.getHeader();
        hibernator.save(header);
        hibernator.save(task);
    }

    @Override
    public List<Task> getTaskByStatus(User user, TaskStatus status) {
        HashMap<String,Object> param = new HashMap<>();
        param.put(HEADER_OWNER, user);
        param.put(STATUS, status);
        param.put(TYPE, new OR(TaskType.handmade, TaskType.accumulative));
        return hibernator.query(Task.class, param);
    }

    @Override
    public Task getTaskByHeader(Object header) {
        return hibernator.get(Task.class, HEADER, header);
    }

    @Override
    public TimeLog getTimeLogByUser(User user) {
        HashMap<String,Object> param = new HashMap<>();
        param.put(OWNER, user);
        param.put(LENGTH, 0);
        return hibernator.get(TimeLog.class, param);
    }

    @Override
    public TimeLog getTimeLog(Object id) {
        return hibernator.get(TimeLog.class, ID, id);
    }

    @Override
    public void saveTimeLog(TimeLog timeLog) {
        hibernator.save(timeLog);
    }

    @Override
    public void removeTasks(User user) {
        hibernator.remove(Task.class, "category/owner", user);
    }

    @Override
    public List<TaskDependency> getDependency(Object task) {
        return hibernator.query(TaskDependency.class, TASK, task);
    }

    @Override
    public List<TaskDependency> getPrincipal(Task task) {
        return hibernator.query(TaskDependency.class, DEPENDENCY, task);
    }

    @Override
    public void removeTask(Task task) {
        hibernator.remove(task);
        final TaskStatistic statistic = getStatistic(task.getHeader().getId());
        if(statistic != null && !statistic.any()){
            hibernator.remove(statistic);
        }
    }

    @Override
    public void saveDependency(TaskDependency dependency) {
        hibernator.save(dependency);
    }

    @Override
    public void removeDependency(TaskDependency dependency) {
        hibernator.remove(dependency);
    }

    @Override
    public TaskStatistic getStatistic(int header) {
        return hibernator.get(TaskStatistic.class, HEADER, header);
    }

    @Override
    public void saveStatistic(TaskStatistic statistic) {
        hibernator.save(statistic);
    }

    @Override
    public void removeStatistic(TaskStatistic statistic) {
        hibernator.remove(statistic);
    }

    @Override
    public TaskStatistic getStatisticOrCreate(Header header) {
        TaskStatistic statistic = getStatistic(header.getId());
        if (statistic == null){
            statistic = new TaskStatistic();
            statistic.setHeader(header);
        }
        return statistic;
    }

    @Override
    public TimeLog getActiveTimeLog(Object o) {
        final HashMap<String, Object> args = new HashMap<>();
        args.put(OWNER, o);
        args.put(LENGTH, 0);
        return hibernator.get(TimeLog.class, args);
    }

    @Override
    public List<TimeLog> getTimeLogList(Header task) {
        return hibernator.query(TimeLog.class, HEADER, task);
    }

    @Override
    public List<TaskStatistic> getChildrenStatistic(Header header) {
        return hibernator.query(TaskStatistic.class, HEADER_PARENT, header);
    }

    @Override
    public TaskDoer getTaskDoerPair(Task task, User user) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(TASK, task);
        args.put(DOER, user);
        return hibernator.get(TaskDoer.class, args);
    }

    @Override
    public void saveTaskDoer(TaskDoer pair) {
        hibernator.save(pair);
    }

    @Override
    public void removeTaskDoer(Task task, User user) {
        final TaskDoer doer = getTaskDoerPair(task, user);
        if(doer != null){
            hibernator.remove(doer);
        }
    }

    @Override
    public void removeTimeLog(TimeLog timeLog) {
        hibernator.remove(timeLog);
    }
}
