package controllers.tree;

import constants.UrlLinks;
import controllers.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

/**
 * Created by DELL on 06.07.2020.
 */
@WebServlet(UrlLinks.TREE)
public class TreePage extends Page {
    private static final String _CONTENT = "/pages/tree/treePage.jsp";
    private static final String _TITLE = "title.tree";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        show(req, resp);
    }
}
