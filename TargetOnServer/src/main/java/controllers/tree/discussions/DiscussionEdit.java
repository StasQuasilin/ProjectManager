package controllers.tree.discussions;

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

import static constants.Keys.*;

@WebServlet(UrlLinks.DISCUSSION_EDIT)
public class DiscussionEdit extends ModalWindow {

    private static final String _TITLE = "title.discussion.edit";
    private static final String _CONTENT = "/pages/tree/discussions/discussionEdit.jsp";
    private final TaskDAO taskDAO = daoService.getTaskDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if(body != null){
            final Task task = taskDAO.getTask(body.get(TASK));
            req.setAttribute(TASK, task);
            req.setAttribute(TITLE, _TITLE);
            req.setAttribute(CONTENT, _CONTENT);
            req.setAttribute(SAVE, ApiLinks.DISCUSSION_EDIT);
            show(req, resp);
        }
    }
}
