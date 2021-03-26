package utils;

import entity.finance.category.Header;
import entity.task.Task;
import entity.task.TaskStatistic;
import entity.task.TaskStatus;
import entity.task.TimeLog;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;

public class TaskUtil {

    private final TaskDAO taskDAO = daoService.getTaskDAO();

    public void checkPossibility(Task task){
//        final List<Task> tasksByParent = taskDAO.getTasksByParent(task.getHeader());

//        TaskStatus status = tasksByParent.size() == 0 ? TaskStatus.active : task.isDoneIfChildren() ? TaskStatus.done : TaskStatus.active;
//
//        for (Task t : tasksByParent) {
//            if (t.getStatus() != TaskStatus.done) {
//                status = TaskStatus.impossible;
//                break;
//            }
//        }
//        task.setStatus(status);
    }

    public void calculateSpendTime(Header header) {
        System.out.println("Calculate time for " + header.getTitle());
        long time = 0;
        for (TimeLog log : taskDAO.getTimeLogList(header)){
            time += log.getLength();
            System.out.println("\t+ " + log.getLength());
        }
        System.out.println("Children statistic:");
        for (TaskStatistic statistic : taskDAO.getChildrenStatistic(header)){
            time += statistic.getSpendTime();
            System.out.println("\t+" + statistic.getHeader().getTitle() + ":" + statistic.getSpendTime());
        }
        final TaskStatistic statistic = taskDAO.getStatisticOrCreate(header);
        statistic.setSpendTime(time);
        taskDAO.saveStatistic(statistic);

        final Header parent = header.getParent();
        if (parent != null){
            calculateSpendTime(parent);
        }

    }

    public void updateStatistic(Header header) {
        TaskStatistic statistic = taskDAO.getStatisticOrCreate(header);

        statistic.cleanChildren();
        for (Task task :  taskDAO.getTasksByParent(header)){
            final TaskStatistic childStatistic = taskDAO.getStatistic(task.getHeader().getId());
            if (childStatistic != null){
                statistic.add(childStatistic);
            }
            final TaskStatus status = task.getStatus();
            if (status == TaskStatus.active){
                statistic.plusActiveChild();
            } else if(status == TaskStatus.progressing){
                statistic.plusProgressingChild();
            } else if (status == TaskStatus.done){
                statistic.plusDoneChildren();
            }
        }

        if (statistic.any()){
            taskDAO.saveStatistic(statistic);
        }else if(statistic.getId() > 0){
            taskDAO.removeStatistic(statistic);
        }

        final Header parent = header.getParent();
        if(parent != null){
            updateStatistic(parent);
        }
    }
}
