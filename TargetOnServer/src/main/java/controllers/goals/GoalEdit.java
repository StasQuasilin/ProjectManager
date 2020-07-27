package controllers.goals;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.ModalWindow;
import entity.user.User;
import utils.db.dao.daoService;
import utils.db.dao.finance.currency.CurrencyDAO;
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
    private static final String _TITLE = "title.goal.edit";
    private final GoalDAO goalDAO = daoService.getGoalDAO();
    private final CurrencyDAO currencyDAO = daoService.getCurrencyDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            req.setAttribute(GOAL, goalDAO.getGoal(body.get(ID)));
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        final User user = getUser(req);
        req.setAttribute(CURRENCY, currencyDAO.getUserCurrency(user));
        req.setAttribute(SAVE, ApiLinks.GOAL_SAVE);
        show(req, resp);
    }
}
