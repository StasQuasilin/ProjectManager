package controllers.finances.transactions;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.ModalWindow;
import entity.finance.transactions.Transaction;
import utils.db.dao.daoService;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(UrlLinks.TRANSACTION_REMOVE)
public class TransactionRemove extends ModalWindow {

    private static final String _TITLE = "title.transaction.remove";
    private static final String _CONTENT = "/pages/finances/transactions/transactionRemove.jsp";
    private final TransactionDAO transactionDAO = daoService.getTransactionDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            Transaction transaction = transactionDAO.getTransaction(body.get(ID));
            req.setAttribute(TRANSACTION, transaction);
            req.setAttribute(TITLE, _TITLE);
            req.setAttribute(CONTENT, _CONTENT);
            req.setAttribute(REMOVE, ApiLinks.TRANSACTION_REMOVE);
            req.setAttribute(EDIT, UrlLinks.TRANSACTION_EDIT);
            show(req, resp);
        }
    }
}
