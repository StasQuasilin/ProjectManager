package controllers.goals;

import constants.UrlLinks;
import controllers.Page;
import subscribe.Subscribe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(UrlLinks.GOALS)
public class GoalsPage extends Page {
    private static final String PAGE = "/pages/goals/goalsList.jsp";
    private static final String _TITLE = "title.goals";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, PAGE);
        req.setAttribute(EDIT, UrlLinks.GOAL_EDIT);
        req.setAttribute(TREE, UrlLinks.TREE);
        req.setAttribute(REMOVE, UrlLinks.GOAL_REMOVE);
        req.setAttribute(SUBSCRIBE, Subscribe.goal);
        show(req, resp);
    }
}
