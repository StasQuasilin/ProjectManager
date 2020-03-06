package utils;

import api.socket.UpdateUtil;
import entity.project.Task;
import entity.project.TaskStatus;
import services.State;
import services.hibernate.dbDAO;
import services.hibernate.dbDAOService;

import java.io.IOException;
import java.util.List;

public class TaskUtil {

    static dbDAO dao = dbDAOService.getDao();
    static UpdateUtil updateUtil = new UpdateUtil();

    public static void checkParenthood(Task parent) {
        List<Task> tasks = dao.getTasksByParent(parent, State.ignore);
        boolean isGroup = tasks.size() > 0;
        if (parent.isGroup() != isGroup){
            parent.setGroup(isGroup);
            dao.save(parent);
            try {
                updateUtil.onSave(parent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
