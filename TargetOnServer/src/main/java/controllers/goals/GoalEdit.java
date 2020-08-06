package controllers.goals;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.ModalWindow;
import entity.finance.buy.BuyList;
import entity.goal.Goal;
import entity.user.User;
import utils.db.dao.daoService;
import utils.db.dao.finance.buy.BuyListDAO;
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
    private final BuyListDAO buyListDAO = daoService.getBuyListDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            final Goal goal = goalDAO.getGoal(body.get(ID));
            req.setAttribute(GOAL, goal);
            if (goal.getBuyList() > 0){
                final BuyList buyList = buyListDAO.getList(goal.getBuyList());
                req.setAttribute(BUY_LIST, buyList);
            }
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        final User user = getUser(req);
        req.setAttribute(CURRENCY, currencyDAO.getUserCurrency(user));
        req.setAttribute(FIND_BUY_LIST, ApiLinks.FIND_BUY_LIST);
        req.setAttribute(SAVE, ApiLinks.GOAL_SAVE);
        show(req, resp);
    }
}
