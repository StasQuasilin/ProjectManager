package api.budget;

import api.socket.UpdateUtil;
import constants.API;
import controllers.ServletAPI;
import entity.budget.Budget;
import entity.budget.Currency;
import entity.transactions.Transaction;
import entity.transactions.TransactionCategory;
import entity.transactions.TransactionType;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.BudgetCalculator;
import utils.CategoryUtil;

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
    private final CategoryUtil categoryUtil = new CategoryUtil();
    private final BudgetCalculator budgetCalculator = new BudgetCalculator();

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

            Date date = Date.valueOf(String.valueOf(body.get(DATE)));
            transaction.setDate(date);

            Budget budget = dao.getObjectById(Budget.class, body.get(BUDGET));
            transaction.setBudget(budget);

            TransactionCategory category = categoryUtil.getCategory((JSONObject) body.get(CATEGORY), user);
            transaction.setCategory(category);

            TransactionType type = TransactionType.valueOf(String.valueOf(body.get(TYPE)));
            transaction.setType(type);

            float sum = Float.parseFloat(String.valueOf(body.get(SUM)));
            if (type == TransactionType.outcome){
                sum *= -1;
            }
            transaction.setSum(sum);

            float rate = Float.parseFloat(String.valueOf(body.get(RATE)));
            transaction.setRate(rate);

            Currency currency = dao.getObjectById(Currency.class, body.get(CURRENCY));
            transaction.setCurrency(currency);

            dao.save(transaction);
            write(resp, SUCCESS);
            updateUtil.onSave(transaction);
            budgetCalculator.calculate(budget, transaction);

        }
    }
}
