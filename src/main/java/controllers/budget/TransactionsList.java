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
 * Created by szpt_user045 on 26.02.2020.
 */
@WebServlet(Branches.TRANSACTIONS)
public class TransactionsList extends IPage {

    private static final Subscribe[] subscribes = new Subscribe[]{
            Subscribe.transactions
    };
    private static final String _TITLE = "title.transactions";
    private static final String _CONTENT = "/pages/transactions/transactions.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(PAGE_CONTENT, _CONTENT);
        req.setAttribute(EDIT, Branches.TRANSACTIONS_EDIT);
        req.setAttribute(PLAN, Branches.TRANSACTION_SETTINGS);
        req.setAttribute(REMOVE, Branches.TRANSACTION_REMOVE);
        req.setAttribute(ACCOUNT_EDIT, Branches.ACCOUNT_EDIT);
        req.setAttribute(TRANSACTIONS, Subscribe.transactions);
        req.setAttribute(FAST_TRANSACTIONS, Subscribe.fast);
        req.setAttribute(ACCOUNTS, Subscribe.accounts);
        showPage(req, resp);
    }
}
