package utils;

import api.socket.UpdateUtil;
import entity.project.Task;
import entity.user.User;
import services.State;
import services.hibernate.dbDAO;
import services.hibernate.dbDAOService;

import java.io.IOException;
import java.util.List;

public class TaskUtil {

    static dbDAO dao = dbDAOService.getDao();
    static UpdateUtil updateUtil = new UpdateUtil();

    public static void checkParenthood(Task parent, User user) {
        List<Task> tasks = dao.getTasksByParent(user, parent, State.ignore);
        int size = tasks.size();
        if (parent.getChildren() != size){
            parent.setChildren(size);
            dao.save(parent);
            try {
                updateUtil.onSave(parent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (Task parent : dao.getObjects(Task.class)){
            List<Task> tasks = dao.getTaskByUserAndParent(parent.getOwner(), parent);
            System.out.println("Task: " + parent.getTitle() + ", children: " + tasks.size());
            parent.setChildren(tasks.size());
            dao.save(parent);

        }
        System.out.println("DONE");
    }
}
