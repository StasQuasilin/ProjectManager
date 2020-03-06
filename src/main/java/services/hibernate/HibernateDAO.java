package services.hibernate;

import constants.Keys;
import entity.budget.*;
import entity.project.Project;
import entity.project.ProjectMember;
import entity.project.Task;
import entity.project.TaskStatus;
import entity.user.User;
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
    public List<Budget> getBudgetsByUser(User user) {
        return hibernator.query(Budget.class, OWNER, user);
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
    public BudgetPoint getBudgetPoint(Budget budget, Date date, PointScale scale) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put(BUDGET, budget);
        params.put(DATE, date);
        params.put(SCALE, scale);
        return hibernator.get(BudgetPoint.class, params);
    }

    @Override
    public List<BudgetPoint> getBudgetPoints(Date from, Date to, Budget budget, PointScale scale) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put(BUDGET, budget);
        if (from != null && to != null) {
            params.put(DATE, new BETWEEN(from, to));
        }
        params.put(SCALE, scale);
        return hibernator.query(BudgetPoint.class, params);
    }

    @Override
    public List<Transaction> getTransactionsByBudget(Budget budget, Date date) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put(BUDGET, budget);
        params.put(DATE, date);

        return hibernator.query(Transaction.class, params);
    }

    @Override
    public List<Task> getTasksByParent(Object parent, Object status) {
        HashMap<String, Object> params = hibernator.getParams();
        params.put(PARENT, parent);
        params.put(STATUS, status);
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
}