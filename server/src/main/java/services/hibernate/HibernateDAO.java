package services.hibernate;

import constants.Keys;
import entity.RegistrationConfirm;
import entity.budget.*;
import entity.calendar.CalendarItem;
import entity.project.Project;
import entity.project.ProjectMember;
import entity.task.Task;
import entity.task.TaskStatus;
import entity.task.TaskStatistic;
import entity.task.TimeLog;
import entity.transactions.Transaction;
import entity.transactions.TransactionCategory;
import entity.transactions.buy.list.BuyList;
import entity.transactions.buy.list.BuyListMember;
import entity.user.User;
import entity.user.UserAccess;
import entity.user.UserSettings;
import services.State;
import services.hibernate.DateContainers.BETWEEN;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by szpt_user045 on 17.02.2020.
 */
public class HibernateDAO implements dbDAO, Keys {
    private Hibernator hibernator = Hibernator.getInstance();

    @Override
    public List<Project> getProjectsByOwner(User user) {
        return hibernator.query(Project.class, OWNER, user.getId());
    }

    @Override
    public List<Project> getProjectsByMembers(User user) {
        List<Project> result = new ArrayList<>();
        for (ProjectMember member : hibernator.query(ProjectMember.class, MEMBER, user)){
            result.add(member.getProject());
        }
        return result;
    }

    @Override
    public <T> T getObjectById(Class<T> tClass, Object id) {
        return hibernator.get(tClass, ID, id);
    }

    @Override
    public List<Task> getTaskByUser(User user) {
        return getTaskByUser(user, TaskStatus.active);
    }

    @Override
    public List<Task> getParents(User user) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put(OWNER, user);
        params.put(PARENT, null);
        return hibernator.query(Task.class, params);
    }

    @Override
    public List<CalendarItem> getCalendarItems(Date date) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put(BEGIN, new BETWEEN(date, Date.valueOf(date.toLocalDate().plusDays(1))));
        return hibernator.query(CalendarItem.class, params);
    }

    @Override
    public List<Task> getTaskToDo(Task parent) {
        List<Task> tasks = new ArrayList<>();
        for (Task task : hibernator.query(Task.class, PARENT, parent)){
            if (task.isGroup()){
                tasks.addAll(getTaskToDo(task));
            } else {
                tasks.add(task);
            }
        }
        return tasks;
    }

    @Override
    public List<Task> getTaskByUser(User user, TaskStatus status) {
        return getTaskByUser(user, status, State.notNull);
    }

    @Override
    public List<Task> getTaskByUser(User user, TaskStatus status, Object parent) {
        return getTaskByUser(user, status, parent, State.ignore);
    }

    @Override
    public List<Task> getTaskByUser(User user, TaskStatus status, Object parent, Object date) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put(OWNER, user);
        params.put(STATUS, status);
        params.put(PARENT, parent);
        params.put(DATE, date);
        return hibernator.query(Task.class, params);
    }

    @Override
    public List<Task> getTaskByDate(User user, Date date) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put(OWNER, user);
        params.put(DATE, date);
        return hibernator.query(Task.class, params);
    }

    @Override
    public void save(Object o) {
        hibernator.save(o);
    }

    @Override
    public List<Account> getBudgetsByUser(User user) {
        return hibernator.query(Account.class, OWNER, user);
    }

    @Override
    public List<Transaction> getTransactionsByUser(User user, Date date) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put(OWNER, user);
        if (date != null){
            params.put(DATE, date);
        }
        return hibernator.query(Transaction.class, params);
    }

    @Override
    public List<TransactionCategory> findTransactionCategory(String key, User user) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put(OWNER, user);
        HashMap<String, String> find = new HashMap<>();
        find.put(NAME, key);
        return hibernator.find(TransactionCategory.class, find, params);
    }

    @Override
    public List<Counterparty> findCounterparty(User user, String key) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put(OWNER, user);
        HashMap<String, String> find = new HashMap<>();
        find.put(NAME, key);
        return hibernator.find(Counterparty.class, find, params);
    }

    @Override
    public BudgetPoint getBudgetPoint(Account account, Date date, PointScale scale) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put(ACCOUNT, account);
        params.put(DATE, date);
        params.put(SCALE, scale);
        return hibernator.get(BudgetPoint.class, params);
    }

    @Override
    public List<BudgetPoint> getBudgetPoints(Date from, Date to, Account account, PointScale scale) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put(ACCOUNT, account);
        if (from != null && to != null) {
            params.put(DATE, new BETWEEN(from, to));
        }
        params.put(SCALE, scale);
        return hibernator.query(BudgetPoint.class, params);
    }

    @Override
    public List<Transaction> getTransactionsByBudget(Account account, Date date) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put(BUDGET, account);
        params.put(DATE, date);

        return hibernator.query(Transaction.class, params);
    }

    @Override
    public List<Task> getTasksByParent(User user, Object parent, Object status) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put(OWNER, user);
        params.put(PARENT, parent);
        params.put(STATUS, status);
        return hibernator.query(Task.class, params);
    }

    @Override
    public List<Task> getTaskByUserAndParent(User user, Object parent) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put(OWNER, user);
        params.put(PARENT, parent);
        return hibernator.query(Task.class, params);
    }

    @Override
    public List<Task> getTaskByDoer(User user, TaskStatus status) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put(DOER, user);
        params.put(STATUS, status);

        return hibernator.query(Task.class, params);
    }

    @Override
    public void remove(Object o) {
        hibernator.delete(o);
    }

    @Override
    public TransactionCategory getCategoryByName(String name, User owner) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put(NAME, name);
        params.put(OWNER, owner);
        return hibernator.get(TransactionCategory.class, params);
    }

    @Override
    public Counterparty getCounterpartyByName(String name, User user) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put(NAME, name);
        params.put(OWNER, user);
        return hibernator.get(Counterparty.class, params);
    }

    @Override
    public List<Transaction> getLimitTransactionsByUser(User user, int limit) {
        return hibernator.query(Transaction.class, "owner", user);
    }

    @Override
    public PointRoot getPointRoot(int parentId, Account account) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put("parentId", parentId);
        params.put("account", account);
        return hibernator.get(PointRoot.class, params);
    }

    @Override
    public boolean removePointRoot(int parentId, Account account, Date date) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put("parentId", parentId);
        params.put("account", account);
        params.put("date", date);
        PointRoot pointRoot = hibernator.get(PointRoot.class, params);
        if (pointRoot != null){
            hibernator.delete(pointRoot);
            return true;
        }
        return false;
    }

    @Override
    public List<BuyList> getBuyListByUser(User user) {
        List<BuyList> list = hibernator.query(BuyList.class, OWNER, user);
        for (BuyListMember member : hibernator.query(BuyListMember.class, MEMBER, user)){
            list.add(member.getList());
        }
        return list;
    }

    @Override
    public List<PointRoot> getPointRoots(Account account, Date date) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put(ACCOUNT, account);
        params.put(DATE, date);
        return hibernator.query(PointRoot.class, params);
    }

    @Override
    public List<UserCurrency> getUserCurrency(User user) {
        return hibernator.query(UserCurrency.class, USER, user);
    }

    @Override
    public <T> List<T> getObjects(Class<T> tClass) {
        return hibernator.query(tClass, null);
    }

    @Override
    public UserAccess getUserAccessByEmail(Object email) {
        return hibernator.get(UserAccess.class, EMAIL, email);
    }

    @Override
    public RegistrationConfirm getRegistrationConfirmByEmail(String email) {
        return hibernator.get(RegistrationConfirm.class, EMAIL, email);
    }

    @Override
    public TaskStatistic getTaskStatistic(Task task) {
        return hibernator.get(TaskStatistic.class, "task", task);
    }

    @Override
    public List<TimeLog> getTimeLogs(Task task) {
        return hibernator.query(TimeLog.class, "task", task);
    }

    @Override
    public TimeLog getActiveTimeLog(Task task) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put("task", task);
        params.put("end", null);
        return hibernator.get(TimeLog.class, params);
    }

    @Override
    public UserSettings getUserSettings(User user) {
        return hibernator.get(UserSettings.class, "user", user);
    }
}
