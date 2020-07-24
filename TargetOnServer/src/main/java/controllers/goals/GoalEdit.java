package controllers.goals;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.ModalWindow;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static constants.Keys.*;

@WebServlet(UrlLinks.GOAL_EDIT)
public class GoalEdit extends ModalWindow {

    private static final String _CONTENT = "/pages/goals/goalEdit.jsp";
    private final GoalDAO goalDAO = daoService.getGoalDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            req.setAttribute(GOAL, goalDAO.getGoal(body.get(ID)));
        }
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(SAVE, ApiLinks.GOAL_SAVE);
        show(req, resp);
    }
}
