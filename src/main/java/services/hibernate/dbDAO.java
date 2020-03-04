package services.hibernate;

import entity.budget.*;
import entity.project.Project;
import entity.project.Task;
import entity.project.TaskStatus;
import entity.user.User;

import java.sql.Date;
import java.util.List;

/**
 * Created by szpt_user045 on 17.02.2020.
 */
public interface dbDAO {

    List<Project> getProjectsByOwner(User user);
    List<Project> getProjectsByMembers(User user);
    <T> T getObjectById(Class<T> tClass, Object id);
    List<Task> getTaskByUser(User user);
    List<Task> getTaskByUser(User user, TaskStatus status);
    List<Task> getTaskByUser(User user, TaskStatus status, Object parent);
    List<Task> getTaskByUser(User user, TaskStatus status, Object parent, Object date);
    List<Task> getTaskByDate(User user, Date date);
    void save(Object o);
    List<Budget> getBudgetsByUser(User user);
    List<Transaction> getTransactionsByUser(User user, Date date);
    List<TransactionCategory> findTransactionCategory(String key, User user);
    BudgetPoint getBudgetPoint(Budget budget, Date date, PointScale scale);
    List<BudgetPoint> getBudgetPoints(Date from, Date to, Budget budget, PointScale scale);
    List<Transaction> getTransactionsByBudget(Budget budget, Date date);
    List<Task> getTaskByParent(Object parent, Object status);
    List<Task> getTaskByDoer(User user, TaskStatus progressing);

    void remove(Object o);
}
