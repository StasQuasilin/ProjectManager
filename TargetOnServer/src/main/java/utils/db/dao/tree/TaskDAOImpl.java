package utils.db.dao.tree;

import entity.finance.category.Header;
import entity.task.*;
import entity.user.User;
import subscribe.Subscribe;
import utils.Updater;
import utils.db.hibernate.Hibernator;

import java.util.HashMap;
import java.util.List;

import static constants.Keys.*;

public class TaskDAOImpl implements TaskDAO {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final Updater updater = new Updater();

    @Override
    public List<Task> getTasksByParent(Header parent) {
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
        updater.update(Subscribe.tree, task, task.getOwner());
    }

    @Override
    public List<Task> getTaskByStatus(User user, TaskStatus status) {
        HashMap<String,Object> param = new HashMap<>();
        param.put(HEADER_OWNER, user);
        param.put(STATUS, status);
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
    public List<TaskDependency> getDependency(Task task) {
        return hibernator.query(TaskDependency.class, TASK, task);
    }

    @Override
    public List<TaskDependency> getPrincipal(Task task) {
        return hibernator.query(TaskDependency.class, DEPENDENCY, task);
    }

    @Override
    public void removeTask(Task task) {
        hibernator.remove(task);
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
    public TaskStatistic getStatistic(Header header) {
        TaskStatistic statistic = hibernator.get(TaskStatistic.class, HEADER, header);
        if (statistic == null){
            statistic = new TaskStatistic();
            statistic.setHeader(header);
        }
        return statistic;
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
        TaskStatistic statistic = getStatistic(header);
        if (statistic == null){
            statistic = new TaskStatistic();
            statistic.setHeader(header);
        }
        return statistic;
    }
}
