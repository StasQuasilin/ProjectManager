package controllers.tree;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.Page;
import entity.task.TaskStatus;
import entity.user.User;
import subscribe.Subscribe;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;
import utils.json.JsonObject;

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
    private final GoalDAO goalDAO = daoService.getGoalDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            if (body.containKey(ITEM)){
                req.setAttribute(ITEM, body.get(ITEM));
            }
        }
        User user = getUser(req);
        req.setAttribute(GOALS, goalDAO.getGoals(user));
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(STATUS, TaskStatus.values());
        req.setAttribute(EDIT, UrlLinks.TASK_EDIT);
        req.setAttribute(GET_TASK, ApiLinks.GET_TASK);
        req.setAttribute(TREE_BUILDER, ApiLinks.TREE_BUILDER);
        req.setAttribute(TASK_TIMER, UrlLinks.TASK_TIMER);
        req.setAttribute(SUBSCRIBE, Subscribe.tree);
        show(req, resp);
    }
}
