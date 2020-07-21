package controllers.finances;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.ModalWindow;
import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionType;
import entity.user.User;
import utils.db.dao.finance.accounts.AccountDAO;
import utils.db.dao.daoService;
import utils.db.dao.finance.currency.CurrencyDAO;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

/**
 * Created by DELL on 06.07.2020.
 */
@WebServlet(UrlLinks.TRANSACTION_EDIT)
public class TransactionEdit extends ModalWindow {

    private static final String _TITLE = "transaction.edit";
    private static final String _CONTENT = "/pages/finances/transactionEdit.jsp";

    private final AccountDAO accountDAO = daoService.getAccountDAO();
    private final CurrencyDAO currencyDAO = daoService.getCurrencyDAO();
    private final TransactionDAO transactionDAO = daoService.getTransactionDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            Transaction transaction = transactionDAO.getTransaction(body.get(ID));
            req.setAttribute(TRANSACTION, transaction);
        }
        req.setAttribute(SAVE, ApiLinks.TRANSACTION_SAVE);
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(TYPES, TransactionType.values());
        User user = getUser(req);
        req.setAttribute(ACCOUNTS, accountDAO.getUserAccounts(user));
        req.setAttribute(CURRENCY, currencyDAO.getUserCurrency(user));
        req.setAttribute(FIND_COUNTERPARTY, ApiLinks.FIND_COUNTERPARTY);
        req.setAttribute(FIND_CATEGORY, ApiLinks.FIND_CATEGORY);
        show(req, resp);
    }


}
