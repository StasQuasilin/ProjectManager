package controllers.api.tree;

import constants.ApiLinks;
import constants.Keys;
import controllers.api.API;
import entity.finance.category.Header;
import entity.task.Task;
import subscribe.Subscribe;
import utils.Updater;
import utils.db.dao.TitleDAO;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(ApiLinks.TASK_DELETE)
public class TaskDeleteApi extends API {

    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final CategoryDAO categoryDAO = daoService.getCategoryDAO();
    private final TitleDAO titleDAO = daoService.getTitleDAO();
    private final Updater updater = new Updater();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            final Task task = taskDAO.getTask(body.get(Keys.ID));
            taskDAO.removeTask(task);
            write(resp, SUCCESS_ANSWER);
            updater.remove(Subscribe.tree, task.getId(), task.getOwner());
            final Header header = task.getHeader();
        }

    }
}
