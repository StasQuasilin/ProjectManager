package utils.db.dao.tree;

import entity.finance.category.Header;
import entity.task.*;
import entity.user.User;

import java.util.List;

public interface TaskDAO {
    List<Task> getTasksByParent(Object parent);
    List<Task> getTaskByOwner(User owner);
    Task getTask(Object id);
    void saveTask(Task task);
    List<Task> getTaskByStatus(User user, TaskStatus active);
    Task getTaskByHeader(Object category);
    TimeLog getTimeLogByUser(User user);
    TimeLog getTimeLog(Object o);
    void saveTimeLog(TimeLog timeLog);
    void removeTasks(User user);
    List<TaskDependency> getDependency(Object task);
    List<TaskDependency> getPrincipal(Task task);
    void removeTask(Task task);

    void saveDependency(TaskDependency remove);

    void removeDependency(TaskDependency dependency);

    TaskStatistic getStatistic(int header);

    void saveStatistic(TaskStatistic statistic);

    void removeStatistic(TaskStatistic statistic);

    TaskStatistic getStatisticOrCreate(Header header);

    TimeLog getActiveTimeLog(Object o);

    List<TimeLog> getTimeLogList(Header task);

    void removeTimeLog(TimeLog timeLog);

    List<TaskStatistic> getChildrenStatistic(Header header);

    TaskDoer getTaskDoerPair(Task task, User user);

    void saveTaskDoer(TaskDoer pair);

    void removeTaskDoer(Task task, User user);
}

