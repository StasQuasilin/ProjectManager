package api.budget;

import constants.API;
import controllers.IAPI;
import utils.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by szpt_user045 on 27.05.2019.
 */
@WebServlet(API.Budget.UPDATE)
public class BudgetListAPI extends IAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<entity.budget.Budget> budgets = hibernator.query(entity.budget.Budget.class, "owner", getUid(req));
        write(resp, JsonParser.toJson(budgets).toJSONString());
    }
}