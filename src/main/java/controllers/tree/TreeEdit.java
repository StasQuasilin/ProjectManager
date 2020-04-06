package controllers.tree;

import constants.API;
import constants.Branches;
import controllers.IModal;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 30.05.2019.
 */
@WebServlet(Branches.TREE_EDIT)
public class TreeEdit extends IModal{
    private static final String _TITLE = "title.task.edit";
    private static final String _CONTENT = "/pages/tree/taskEdit.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            req.setAttribute(PARENT, body.get(PARENT));
            req.setAttribute(TITLE, _TITLE);
            req.setAttribute(PAGE_CONTENT, _CONTENT);
            req.setAttribute(SAVE, API.Tree.EDIT_TASK);
            show(req, resp);
        }
    }
}
