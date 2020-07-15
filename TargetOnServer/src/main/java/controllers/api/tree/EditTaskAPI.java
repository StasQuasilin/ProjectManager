package controllers.api.tree;

import constants.ApiLinks;
import controllers.api.API;
import entity.finance.Category;
import entity.task.Task;
import entity.task.TaskStatus;
import entity.user.User;
import utils.db.dao.calendar.CalendarDAO;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;
import utils.json.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static constants.Keys.*;

@WebServlet(ApiLinks.TASK_EDIT)
public class EditTaskAPI extends API {

    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final CategoryDAO categoryDAO = daoService.getCategoryDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            Task task = taskDAO.getTask(body.get(ID));
            if (task == null){
                task = new Task();
                task.setUid(UUID.randomUUID().toString());
                task.setStatus(TaskStatus.active);
            }
            final User user = getUser(req);
            Category category = task.getCategory();
            if (category == null){
                category = new Category();
                category.setOwner(user);
                task.setCategory(category);
            }

            if (body.containKey(PARENT)){
                final Category parent = categoryDAO.getCategory(body.get(PARENT));
                category.setParent(parent);
            } else {
                category.setParent(null);
            }

            final String title = body.getString(TITLE);
            category.setTitle(title);

            write(resp, SUCCESS_ANSWER);
            taskDAO.saveTask(task);

            categoryDAO.updateStatistic(task.getCategory());
        }
    }
}
