package api.tree;

import api.socket.UpdateUtil;
import constants.API;
import controllers.ServletAPI;
import entity.task.Task;
import entity.task.TaskStatus;
import entity.transactions.TransactionCategory;
import entity.user.User;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.TaskUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(API.Tree.EDIT_TASK)
public class EditTaskAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(EditTaskAPI.class);
    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            log.info(body);
            User user = getUser(req);
            Task task = dao.getObjectById(Task.class, body.get(ID));
            if (task == null){
                task = new Task();
                task.setCategory(new TransactionCategory());
                task.setStatus(TaskStatus.active);
                task.setOwner(user);
            }

            Task parent = dao.getObjectById(Task.class, body.get(PARENT));
            task.setParent(parent);

            String title = String.valueOf(body.get(TITLE));
            task.getCategory().setName(title);

            dao.save(task.getCategory());
            dao.save(task);

            write(resp, SUCCESS);
            updateUtil.onSave(task);
            TaskUtil.checkParenthood(parent, user);
        }
    }
}
