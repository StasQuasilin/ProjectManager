package controllers.transactions;

import constants.API;
import constants.Branches;
import controllers.IModal;
import entity.transactions.TransactionRepeat;
import entity.transactions.TransactionSettings;
import entity.user.User;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.TRANSACTION_SETTINGS)
public class TransactionSettingsEdit extends IModal {
    private static final String _TITLE = "title.transaction.settings.edit";
    private static final String _CONTENT = "/pages/transactions/transactionSettings.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            TransactionSettings settings = dao.getObjectById(TransactionSettings.class, body.get(ID));
            req.setAttribute(SETTING, settings);
        }
        User user = getUser(req);
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(PAGE_CONTENT, _CONTENT);
        req.setAttribute(REPEATS, TransactionRepeat.values());
        req.setAttribute(ACCOUNTS, dao.getBudgetsByUser(user));
        req.setAttribute(CURRENCY, dao.getUserCurrency(user));
        req.setAttribute(FIND_CATEGORY, API.FIND_TRANSACTION_CATEGORY);
        req.setAttribute(SAVE, API.TRANSACTION_SETTINGS_EDIT);
        show(req, resp);
    }

}
