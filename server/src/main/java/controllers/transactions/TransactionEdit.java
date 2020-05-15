package controllers.transactions;

import constants.API;
import constants.Branches;
import controllers.IModal;
import entity.transactions.Transaction;
import entity.transactions.TransactionType;
import entity.user.User;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 26.02.2020.
 */
@WebServlet(Branches.TRANSACTIONS_EDIT)
public class TransactionEdit extends IModal {
    private static final String _CONTENT = "/pages/transactions/transactionEdit.jsp";
    private static final String _TITLE = "title.transaction.edit";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Transaction transaction = dao.getObjectById(Transaction.class, body.get(ID));
            req.setAttribute(TRANSACTION, transaction);
        }
        User user = getUser(req);
        req.setAttribute(TYPES, TransactionType.values());
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(PAGE_CONTENT, _CONTENT);
        req.setAttribute(CURRENCY, dao.getUserCurrency(user));
        req.setAttribute(ACCOUNTS, dao.getAccountsByUser(user));
        req.setAttribute(FIND_CATEGORY, API.FIND_TRANSACTION_CATEGORY);
        req.setAttribute(FIND_PERSON, API.FIND_PERSON);
        req.setAttribute(FIND_COUNTERPARTY, API.FIND_COUNTERPARTY);
        req.setAttribute(SAVE, API.TRANSACTION_EDIT);
        show(req, resp);
    }
}
