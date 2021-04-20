package controllers.calendar;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.ModalWindow;
import entity.user.User;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(UrlLinks.RANDOM_TASK)
public class RandomTask extends ModalWindow {

    private static final String _TITLE = "title.random.task";
    private static final String _CONTENT = "/pages/calendar/randomTask.jsp";
    private final GoalDAO goalDAO = daoService.getGoalDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final User user = getUser(req);
        req.setAttribute(GOALS, goalDAO.getGoals(user));
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(RANDOM_TASK, ApiLinks.RANDOM_TASK);
        req.setAttribute(SAVE, ApiLinks.SAVE_RANDOM_TASK);
        show(req, resp);
    }
}
