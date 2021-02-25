package controllers.tree;

import constants.ApiLinks;
import constants.Keys;
import constants.UrlLinks;
import controllers.ModalWindow;
import entity.task.Task;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(UrlLinks.TASK_DELETE)
public class TaskDelete extends ModalWindow {

    private static final String _TITLE = "title.task.delete";
    private static final String CONTENT = "/pages/tree/taskDelete.jsp";
    private final TaskDAO taskDAO = daoService.getTaskDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            final Task task = taskDAO.getTask(body.get(Keys.ID));
            req.setAttribute(Keys.TASK, task);
            req.setAttribute(Keys.TITLE, _TITLE);
            req.setAttribute(Keys.CONTENT, CONTENT);
            req.setAttribute(Keys.DELETE, ApiLinks.TASK_DELETE);
            show(req, resp);
        }
    }
}
