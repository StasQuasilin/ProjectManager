package controllers.budget;

import api.socket.Subscribe;
import constants.Branches;
import controllers.IPage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by quasilin on 24.02.2019.
 */
@WebServlet(Branches.BUDGET)
public class BudgetList extends IPage {

    private static final Subscribe[] subscribes = new Subscribe[]{
            Subscribe.accounts
    };

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, "accounts.title");
        req.setAttribute(PAGE_CONTENT, "/pages/budget/budgetList.jsp");
        req.setAttribute(EDIT, Branches.ACCOUNT_EDIT);
        req.setAttribute(SUBSCRIBES, subscribes);
        showPage(req, resp);
    }
}
