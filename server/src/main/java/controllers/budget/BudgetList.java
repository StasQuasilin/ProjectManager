package controllers.budget;

import constants.API;
import constants.Links;
import controllers.IPage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by quasilin on 24.02.2019.
 */
@WebServlet(Links.BUDGET)
public class BudgetList extends IPage {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "budget.title");
        req.setAttribute("pageContent", "/pages/budget/budgetList.jsp");
        req.setAttribute("edit", API.Budget.EDIT);
        req.setAttribute("update", API.Budget.UPDATE);
        showPage(req, resp);
    }
}
