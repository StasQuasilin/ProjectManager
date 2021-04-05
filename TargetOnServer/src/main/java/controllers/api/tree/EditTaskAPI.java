package controllers.api.tree;

import constants.ApiLinks;
import constants.Keys;
import controllers.api.API;
import entity.finance.category.Header;
import entity.finance.category.HeaderType;
import entity.task.Task;
import entity.task.TaskDependency;
import entity.task.TaskStatus;
import entity.task.TaskType;
import entity.user.User;
import org.json.simple.JSONArray;
import utils.TaskToBuyListUtil;
import utils.TaskUtil;
import utils.db.dao.TitleDAO;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;
import utils.json.JsonObject;
import utils.savers.TaskSaver;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import static constants.Keys.*;

@WebServlet(ApiLinks.TASK_EDIT)
public class EditTaskAPI extends API {

    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final TaskSaver taskSaver = new TaskSaver();
    private final TaskToBuyListUtil buyListUtil = new TaskToBuyListUtil();
    private final TaskUtil taskUtil = new TaskUtil();
    private final TitleDAO titleDAO = daoService.getTitleDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            final User user = getUser(req);
            Task task = taskDAO.getTask(body.get(ID));
            if (task == null){
                task = new Task();
                task.setUid(UUID.randomUUID().toString());
            }
            if(body.containKey(TYPE)){
                TaskType type = TaskType.valueOf(body.getString(TYPE));
                task.setType(type);
            } else {
                task.setType(TaskType.handmade);
            }
            final TaskType type = task.getType();
            if (type != TaskType.accumulative) {
                if (body.containKey(STATUS)) {
                    TaskStatus status = TaskStatus.valueOf(body.getString(STATUS));
                    final TaskStatus oldStatus = task.getStatus();
                    task.setStatus(status);
                    if(oldStatus == TaskStatus.progressing){
                        taskDAO.removeTaskDoer(task, getUser(req));
                    } else if(status == TaskStatus.progressing){
                        taskUtil.addTaskDoer(task, user);
                    }
                } else {
                    task.setStatus(TaskStatus.active);
                }
            } else {
                float progress = body.getFloat(PROGRESS);
                float target = body.getFloat(TARGET);
                task.setProgress(progress);
                task.setTarget(target);
                task.setStatus(progress >= target ? TaskStatus.done : TaskStatus.active);
            }

            if (body.containKey(DEADLINE)){
                Date deadline = body.getDate(DEADLINE);
                task.setDeadline(deadline);
            } else {
                task.setDeadline(null);
            }

            boolean doneIf = body.getBoolean(DONE_IF);
            task.setDoneIfChildren(doneIf);


            Header header = task.getHeader();
            if (header == null){
                header = new Header();
                header.setType(HeaderType.task);
                header.setOwner(user);
                task.setHeader(header);
            }

            final Header oldParent = header.getParent();
            Header parent = null;
            if (body.containKey(PARENT)){
                parent = titleDAO.getHeader(body.get(PARENT));
                header.setParent(parent);
//                final Task parentTask = taskDAO.getTaskByHeader(parent);
//                if (parentTask != null) {
//                    taskDAO.saveTask(task);
//                    taskUtil.checkPossibility(parentTask);
//                    taskSaver.save(parentTask);
//                }
            } else {
//                final Header parent = header.getParent();
                header.setParent(null);
//                taskDAO.saveTask(task);
//                if (parent != null){
//                    final Task parentTask = taskDAO.getTaskByCategory(parent);
//                    taskDAO.saveTask(task);
//                    taskUtil.checkPossibility(parentTask);
//                    taskSaver.save(parentTask);
//                }
            }

            final String title = body.getString(TITLE);
            header.setValue(title);

            final float coast = body.getFloat(COAST);
            task.setCoast(coast);

            write(resp, SUCCESS_ANSWER);
            taskSaver.save(task);

            if (parent != null){
                taskUtil.updateStatistic(parent);
            }
            if(oldParent != null && !oldParent.equals(parent)){
                taskUtil.updateStatistic(oldParent);
            }

            if(saveDependency(task, body.getJsonArray(DEPENDENCY)) != 0){
                taskSaver.update(task);
            }

            if (body.containKey(BUY_LIST)){
                buyListUtil.taskToBuyList(task, new JsonObject(body.get(BUY_LIST)));
            } else {
                buyListUtil.remove(task);
            }
        }
    }

    private int saveDependency(Task task, JSONArray dependency) {
        final HashMap<Integer, TaskDependency> dependencyMap = buildDependencyMap(task);
        int update = dependencyMap.size() - dependency.size();
        final Set<TaskDependency> dependencies = task.getDependencies();
        dependencies.clear();
        int activeDep = 0;
        for (Object o : dependency){
            JsonObject object = new JsonObject(o);
            final int id = object.getInt(ID);
            TaskDependency remove = dependencyMap.remove(id);
            if (remove == null){
                remove = new TaskDependency();
                remove.setTask(task);
                remove.setDependency(taskDAO.getTaskByHeader(id));
                taskDAO.saveDependency(remove);
            }

            final Task dep = remove.getDependency();
            if (dep.getStatus() == TaskStatus.active){
                activeDep++;
            }
            dependencies.add(remove);
        }

        if (dependencies.size() > 0 && activeDep > 0) {
            if (task.getStatus() != TaskStatus.impossible) {
                task.setStatus(TaskStatus.impossible);
                saveTask(task);
                update = 0;
            }
        } else {
            if (task.getStatus() != TaskStatus.done) {
                if (task.getStatus() != TaskStatus.active) {
                    task.setStatus(TaskStatus.active);
                    saveTask(task);
                    update = 0;
                }
            }
        }
        for (TaskDependency taskDependency : dependencyMap.values()){
            taskDAO.removeDependency(taskDependency);
        }
        return update;
    }

    private void saveTask(Task task){
        taskDAO.saveTask(task);
        taskUtil.updateStatistic(task.getHeader());
    }

    private HashMap<Integer, TaskDependency> buildDependencyMap(Task task) {
        final HashMap<Integer, TaskDependency> map = new HashMap<>();
        for (TaskDependency dependency : taskDAO.getDependency(task)){
            final Task d = dependency.getDependency();
            map.put(d.getId(), dependency);
        }
        return map;
    }
}
