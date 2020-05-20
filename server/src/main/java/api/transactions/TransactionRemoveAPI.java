package api.transactions;

import api.socket.UpdateUtil;
import constants.API;
import controllers.ServletAPI;
import entity.accounts.Account;
import entity.transactions.Transaction;
import org.json.simple.JSONObject;
import utils.BudgetCalculator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet(API.TRANSACTION_REMOVE)
public class TransactionRemoveAPI extends ServletAPI {

    private final BudgetCalculator budgetCalculator = new BudgetCalculator();
    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Transaction transaction = dao.getObjectById(Transaction.class, body.get(ID));
            int id = transaction.getId();
            Date date = transaction.getDate();
            Account account = transaction.getAccount();
            if (account != null){
                budgetCalculator.removePointRoot(id, account.getId(), date);
            }
            Account secondary = transaction.getSecondary();
            if (secondary != null){
                budgetCalculator.removePointRoot(id, secondary.getId(), date);
            }
            dao.remove(transaction);
            write(resp, SUCCESS);
            updateUtil.onRemove(transaction);
        }
    }
}
