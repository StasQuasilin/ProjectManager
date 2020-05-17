package controllers.transactions.fast;

import constants.API;
import constants.Branches;
import controllers.IModal;
import entity.transactions.TransactionType;
import entity.transactions.fast.transaction.FastTransaction;
import entity.user.User;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.FAST_EDIT)
public class FastTransactionEdit extends IModal {

    private static final String _CONTENT = "/pages/transactions/transactionEdit.jsp";
    private static final String _TITLE = "title.transaction.edit";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            FastTransaction fast = dao.getObjectById(FastTransaction.class, body.get(ID));
            req.setAttribute(TRANSACTION, fast);
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
        req.setAttribute(SAVE, API.FAST_EDIT);
        req.setAttribute(DATE_SELECT, false);
        show(req, resp);
    }
}
