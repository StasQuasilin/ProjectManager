package api.transactions;

import api.socket.UpdateUtil;
import constants.API;
import controllers.ServletAPI;
import entity.accounts.Account;
import entity.transactions.Transaction;
import entity.transactions.TransactionCategory;
import entity.transactions.TransactionType;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.BudgetCalculator;
import utils.Calculator;
import utils.CategoryCalculator;
import utils.TransactionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by szpt_user045 on 26.02.2020.
 */
@WebServlet(API.TRANSACTION_EDIT)
public class TransactionEditAPI extends ServletAPI {


    private final UpdateUtil updateUtil = new UpdateUtil();
    private final BudgetCalculator budgetCalculator = new BudgetCalculator();
    private final TransactionUtil transactionUtil = new TransactionUtil();
    private final CategoryCalculator categoryCalculator = new CategoryCalculator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);

            Transaction transaction = dao.getObjectById(Transaction.class, body.get(ID));
            User user = getUser(req);

            Date otherDate = null;
            Account otherAccount = null;
            Account otherSecondary = null;
            TransactionCategory otherCategory = null;

            if (transaction == null){
                transaction = new Transaction();
                transaction.setOwner(user);
            } else {
                otherDate = transaction.getDate();
                otherAccount = transaction.getAccount();
                otherSecondary = transaction.getSecondary();
                otherCategory = transaction.getCategory();
            }
            transactionUtil.filTransaction(transaction, body, user);

            dao.save(transaction);
            write(resp, SUCCESS);
            updateUtil.onSave(transaction);

            if (otherDate == null){
                otherDate = transaction.getDate();
            }

            if (otherAccount != null && !transaction.getAccount().equals(otherAccount)){
                budgetCalculator.removePointRoot(transaction.getId(), otherAccount.getId(), otherDate);
            }

            TransactionType type = transaction.getType();

            if (type == TransactionType.transfer){
                if (otherSecondary != null && !transaction.getSecondary().equals(otherAccount)){
                    budgetCalculator.removePointRoot(transaction.getId(), otherSecondary.getId(), otherDate);
                }
            }

            budgetCalculator.calculatePointRoot(transaction);

            if (otherCategory != null && !transaction.getCategory().equals(otherCategory)){
                categoryCalculator.removePointRoot(transaction.getId(), otherCategory.getId(), otherDate);

            }
            categoryCalculator.calculate(transaction);
        }
    }
}
