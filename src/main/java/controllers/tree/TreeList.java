package controllers.tree;

import api.socket.Subscribe;
import constants.API;
import constants.Branches;
import controllers.IPage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by quasilin on 23.02.2019.
 */
@WebServlet(Branches.TREE)
public class TreeList extends IPage {

    private static final String _TITLE = "tree.title";
    private static final String _CONTENT = "/pages/tree/treeList.jsp";
    private static final Subscribe[] subscribes = new Subscribe[]{Subscribe.tree};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(PAGE_CONTENT, _CONTENT);
        req.setAttribute(GET_SUB_TASKS, API.GET_SUB_TASK);
        req.setAttribute(EDIT, Branches.TREE_EDIT);
        req.setAttribute(SETTING, Branches.TASK_SETTING);
        req.setAttribute(REMOVE, Branches.TASK_REMOVE);
        req.setAttribute(SUBSCRIBES, subscribes);
        showPage(req, resp);
    }
}
