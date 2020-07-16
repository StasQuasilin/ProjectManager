package controllers.tree;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.ModalWindow;
import entity.finance.transactions.Category;
import entity.task.Task;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(UrlLinks.TASK_EDIT)
public class TaskEdit extends ModalWindow {
    private static final String _TITLE = "title.task.edit";
    private static final String _CONTENT = "/pages/tree/taskEdit.jsp";

    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final CategoryDAO categoryDAO = daoService.getCategoryDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            final Task task = taskDAO.getTask(body.get(ID));
            final Category parent = categoryDAO.getCategory(body.get(PARENT));
            req.setAttribute(TASK, task);
            req.setAttribute(PARENT, parent);
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(SAVE, ApiLinks.TASK_EDIT);
        show(req, resp);
    }
}
