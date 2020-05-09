package utils;

import api.socket.UpdateUtil;
import entity.task.Task;
import entity.task.TaskStatus;
import entity.task.TaskStatistic;
import entity.task.TimeLog;
import entity.transactions.Transaction;
import entity.transactions.TransactionDetail;
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

        TaskStatistic statistic = dao.getTaskStatistic(parent);
        if (statistic == null){
            statistic = new TaskStatistic();
            statistic.setTask(parent);
        }

        statistic.setActiveChildren(0);
        statistic.setProgressingChildren(0);
        statistic.setDoneChildren(0);

        for (Task task : tasks){
            TaskStatus status = task.getStatus();
            if (!counts.containsKey(status)){
                counts.put(status, 0);
            }
            counts.put(status, counts.get(status) + 1);

            statistic.setActiveChildren(statistic.getActiveChildren() + task.getChildrenCount(TaskStatus.active));
            statistic.setProgressingChildren(statistic.getProgressingChildren() + task.getChildrenCount(TaskStatus.progressing));
            statistic.setDoneChildren(statistic.getDoneChildren() + task.getChildrenCount(TaskStatus.done));
        }

        if (counts.containsKey(TaskStatus.active))
        statistic.setActiveChildren(statistic.getActiveChildren() + counts.get(TaskStatus.active));
        if (counts.containsKey(TaskStatus.progressing))
        statistic.setProgressingChildren(statistic.getProgressingChildren() + counts.get(TaskStatus.progressing));
        if (counts.containsKey(TaskStatus.done))
        statistic.setDoneChildren(statistic.getDoneChildren() + counts.get(TaskStatus.done));
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
            System.out.println("\t...Have parent");
            checkParenthood(parent.getParent(), user);
        }
    }

    public static void main(String[] args) {
        Hibernator hibernator = Hibernator.getInstance();

        for (Transaction transaction : hibernator.query(Transaction.class, null)){
            TransactionDetail detail = new TransactionDetail();

            detail.setTransaction(transaction);
            detail.setSum(transaction.getAmount());

            hibernator.save(detail);
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
