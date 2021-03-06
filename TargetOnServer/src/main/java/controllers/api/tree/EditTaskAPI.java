package controllers.api.tree;

import constants.ApiLinks;
import controllers.api.API;
import entity.finance.category.Header;
import entity.finance.category.HeaderType;
import entity.task.Task;
import entity.task.TaskStatus;
import entity.user.User;
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
import java.util.UUID;

import static constants.Keys.*;

@WebServlet(ApiLinks.TASK_EDIT)
public class EditTaskAPI extends API {

    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final CategoryDAO categoryDAO = daoService.getCategoryDAO();
    private final TaskSaver taskSaver = new TaskSaver();
    private final TaskToBuyListUtil buyListUtil = new TaskToBuyListUtil();
    private final TaskUtil taskUtil = new TaskUtil();
    private final TitleDAO titleDAO = daoService.getTitleDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            Task task = taskDAO.getTask(body.get(ID));
            if (task == null){
                task = new Task();
                task.setUid(UUID.randomUUID().toString());
            }

            if (body.containKey(STATUS)){
                TaskStatus status = TaskStatus.valueOf(body.getString(STATUS));
                task.setStatus(status);
            } else {
                task.setStatus(TaskStatus.active);
            }

            if (body.containKey(DEADLINE)){
                Date deadline = body.getDate(DEADLINE);
                task.setDeadline(deadline);
            } else {
                task.setDeadline(null);
            }

            boolean doneIf = body.getBoolean(DONE_IF);
            task.setDoneIfChildren(doneIf);

            final User user = getUser(req);
            Header header = task.getHeader();
            if (header == null){
                header = new Header();
                header.setType(HeaderType.task);
                header.setOwner(user);
                task.setHeader(header);
            }

            if (body.containKey(PARENT)){
                final Header parent = titleDAO.getHeader(body.get(PARENT));
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

            write(resp, SUCCESS_ANSWER);
            taskSaver.save(task);

            if (body.containKey(BUY_LIST)){
                buyListUtil.taskToBuyList(task, new JsonObject(body.get(BUY_LIST)));
            } else {
                buyListUtil.remove(task);
            }
        }
    }
}
