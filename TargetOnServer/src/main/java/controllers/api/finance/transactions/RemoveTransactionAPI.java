package controllers.api.finance.transactions;

import constants.ApiLinks;
import controllers.api.API;
import entity.finance.transactions.Transaction;
import utils.db.dao.daoService;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.json.JsonObject;
import utils.removers.TransactionRemover;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.ID;

@WebServlet(ApiLinks.TRANSACTION_REMOVE)
public class RemoveTransactionAPI extends API {

    private final TransactionDAO transactionDAO = daoService.getTransactionDAO();
    private final TransactionRemover remover = new TransactionRemover();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            final Transaction transaction = transactionDAO.getTransaction(body.get(ID));
            write(resp, SUCCESS_ANSWER);
            remover.removeTransaction(transaction);
        } else{
            write(resp, SUCCESS_ANSWER);
        }


    }
}
