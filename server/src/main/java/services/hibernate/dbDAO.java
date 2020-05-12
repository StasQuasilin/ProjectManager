package services.hibernate;

import entity.RegistrationConfirm;
import entity.budget.*;
import entity.calendar.CalendarItem;
import entity.project.Project;
import entity.task.Task;
import entity.task.TaskStatus;
import entity.task.TaskStatistic;
import entity.task.TimeLog;
import entity.transactions.Transaction;
import entity.transactions.TransactionCategory;
import entity.user.User;
import entity.user.UserAccess;
import entity.user.UserSettings;

import java.sql.Date;
import java.util.List;

/**
 * Created by szpt_user045 on 17.02.2020.
 */
public interface dbDAO {

    List<Project> getProjectsByOwner(User user);
    List<Project> getProjectsByMembers(User user);
    <T> T getObjectById(Class<T> tClass, Object id);
    List<Task> getTaskByUserAndParent(User user, Object parent);
    List<Task> getTaskByUser(User user);
    List<Task> getTaskByUser(User user, TaskStatus status);
    List<Task> getTaskByUser(User user, TaskStatus status, Object parent);
    List<Task> getTaskByUser(User user, TaskStatus status, Object parent, Object date);
    List<Task> getTaskByDate(User user, Date date);
    void save(Object o);
    List<Account> getBudgetsByUser(User user);
    List<Transaction> getTransactionsByUser(User user, Date date);
    List<TransactionCategory> findTransactionCategory(String key, User user);
    BudgetPoint getBudgetPoint(Account account, Date date, PointScale scale);
    List<BudgetPoint> getBudgetPoints(Date from, Date to, Account account, PointScale scale);
    List<Transaction> getTransactionsByBudget(Account account, Date date);
    List<Task> getTasksByParent(User user, Object parent, Object status);
    List<Task> getTaskByDoer(User user, TaskStatus progressing);
    void remove(Object o);
    TransactionCategory getCategoryByName(String name, User owner);
    List<UserCurrency> getUserCurrency(User user);
    <T> List<T> getObjects(Class<T> tClass);
    UserAccess getUserAccessByEmail(Object email);
    RegistrationConfirm getRegistrationConfirmByEmail(String email);
    List<Task> getTaskToDo(Task parent);
    List<Task> getParents(User user);
    List<CalendarItem> getCalendarItems(Date date);
    TaskStatistic getTaskStatistic(Task parent);
    List<TimeLog> getTimeLogs(Task task);
    TimeLog getActiveTimeLog(Task task);
    UserSettings getUserSettings(User user);
    List<Counterparty> findCounterparty(User user, String key);
    Counterparty getCounterpartyByName(String name, User user);
    List<Transaction> getLimitTransactionsByUser(User user, int limit);
    PointRoot getPointRoot(int parentId, Account account);
    List<PointRoot> getPointRoots(Account account, Date date);
    boolean removePointRoot(int parentId, Account account, Date date);
}
