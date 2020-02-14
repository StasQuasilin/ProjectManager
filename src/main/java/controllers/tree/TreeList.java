package controllers.tree;

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "tree.title");
        req.setAttribute("pageContent", "/pages/tree/treeList.jsp");
        req.setAttribute("edit", Branches.TREE_EDIT);
        req.setAttribute("update", API.Tree.LIST);
        showPage(req, resp);
    }
}
