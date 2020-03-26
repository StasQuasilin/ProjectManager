package api.task;

import api.socket.UpdateUtil;
import constants.API;
import controllers.ServletAPI;
import entity.budget.Budget;
import entity.project.Task;
import org.json.simple.JSONObject;
import utils.BudgetCalculator;
import utils.TaskUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(API.TASK_REMOVE)
public class TaskRemoveAPI extends ServletAPI {

    private final UpdateUtil updateUtil = new UpdateUtil();
    private final BudgetCalculator budgetCalculator = new BudgetCalculator();
    private final TaskUtil taskUtil = new TaskUtil();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Task task = dao.getObjectById(Task.class, body.get(ID));
            dao.remove(task);
            write(resp, SUCCESS);
            updateUtil.onRemove(task);
            Task parent = task.getParent();
            if (parent != null){
                TaskUtil.checkParenthood(parent, getUser(req));
            }


        }
    }
}
