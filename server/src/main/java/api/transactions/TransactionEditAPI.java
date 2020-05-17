package api.transactions;

import api.socket.UpdateUtil;
import constants.API;
import constants.Keys;
import controllers.ServletAPI;
import entity.accounts.Account;
import entity.accounts.Counterparty;
import entity.accounts.Currency;
import entity.transactions.Transaction;
import entity.transactions.TransactionCategory;
import entity.transactions.TransactionType;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.BudgetCalculator;
import utils.CategoryUtil;
import utils.CounterpartyUtil;
import utils.TransactionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 26.02.2020.
 */
@WebServlet(API.TRANSACTION_EDIT)
public class TransactionEditAPI extends ServletAPI {


    private final UpdateUtil updateUtil = new UpdateUtil();


    private final BudgetCalculator budgetCalculator = new BudgetCalculator();
    private final TransactionUtil transactionUtil = new TransactionUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);

            Transaction transaction = dao.getObjectById(Transaction.class, body.get(ID));
            User user = getUser(req);

            if (transaction == null){
                transaction = new Transaction();
                transaction.setOwner(user);
            }

            Date otherDate = null;
            Account otherAccount = null;
            Account otherSecondary = null;

            if (transaction.getId() > 0){
                otherDate = transaction.getDate();
                otherAccount = transaction.getAccount();
                otherSecondary = transaction.getSecondary();
            }

            transactionUtil.filTransaction(transaction, body, user);

            if (otherAccount != null && !transaction.getAccount().equals(otherAccount)){
                if (otherDate == null){
                    otherDate = transaction.getDate();
                }
                budgetCalculator.removePointRoot(transaction.getId(), otherAccount, otherDate);
            }

            TransactionType type = transaction.getType();

            if (type == TransactionType.transfer){
                if (otherDate == null){
                    otherDate = transaction.getDate();
                }
                if (otherSecondary != null && !transaction.getSecondary().equals(otherAccount)){
                    budgetCalculator.removePointRoot(transaction.getId(), otherSecondary, otherDate);
                }
            }

            dao.save(transaction);
            write(resp, SUCCESS);
            updateUtil.onSave(transaction);
            budgetCalculator.calculatePointRoot(transaction);
        }
    }
}
