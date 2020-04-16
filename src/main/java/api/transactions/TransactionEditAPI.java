package api.transactions;

import api.socket.UpdateUtil;
import constants.API;
import constants.Keys;
import controllers.ServletAPI;
import entity.budget.Budget;
import entity.budget.Counterparty;
import entity.budget.Currency;
import entity.transactions.Transaction;
import entity.transactions.TransactionCategory;
import entity.transactions.TransactionType;
import entity.user.User;
import org.json.simple.JSONObject;
import utils.BudgetCalculator;
import utils.CategoryUtil;
import utils.CounterpartyUtil;

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
    private final CounterpartyUtil counterpartyUtil = new CounterpartyUtil();
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

            Date prevDate;
            Date date = Date.valueOf(String.valueOf(body.get(DATE)));
            if (transaction.getDate() == null){
                prevDate = date;
            } else {
                prevDate = transaction.getDate();
            }
            transaction.setDate(date);

            Budget prevBudget = null;
            Budget budget = dao.getObjectById(Budget.class, body.get(BUDGET));
            if (transaction.getBudget() != null && transaction.getBudget().getId() != budget.getId()){
                prevBudget = transaction.getBudget();
            }

            transaction.setBudget(budget);

            if (body.containsKey(CATEGORY)) {
                TransactionCategory category = categoryUtil.getCategory((JSONObject) body.get(CATEGORY), user);
                transaction.setCategory(category);
            }

            TransactionType type = TransactionType.valueOf(String.valueOf(body.get(TYPE)));
            transaction.setType(type);

            float sum = Float.parseFloat(String.valueOf(body.get(SUM)));
            if (type == TransactionType.outcome || type == TransactionType.transfer){
                sum *= -1;
            }
            transaction.setSum(sum);

            float rate = Float.parseFloat(String.valueOf(body.get(RATE)));
            transaction.setRate(rate);

            Currency currency = dao.getObjectById(Currency.class, body.get(CURRENCY));
            transaction.setCurrency(currency);

            if (body.containsKey(COUNTERPARTY)) {
                Counterparty counterparty = counterpartyUtil.getCounterparty((JSONObject) body.get(COUNTERPARTY), user);
                transaction.setCounterparty(counterparty);
            } else if (transaction.getCounterparty() != null){
                transaction.setCounterparty(null);
            }

            if (body.containsKey(COMMENT)) {
                String comment = String.valueOf(body.get(COMMENT));
                transaction.setComment(comment);
            } else if (transaction.getComment() != null){
                transaction.setComment(Keys.EMPTY);
            }

            if (type == TransactionType.transfer){
                Transaction child = transaction.getChild();
                if (child == null){
                    child = new Transaction();
                    child.setOwner(user);
                    transaction.setChild(child);
                }
                Budget transferTo = dao.getObjectById(Budget.class, body.get(TRANSFER_TO));
                child.setType(type);
                child.setDate(date);
                child.setBudget(transferTo);
                child.setSum(sum * -1);
                child.setRate(rate);
                child.setCurrency(dao.getObjectById(Currency.class, transferTo.getCurrency()));
                dao.save(child);
                budgetCalculator.calculate(transferTo, date);
                transaction.setChild(child);
            }

            dao.save(transaction);
            write(resp, SUCCESS);
            updateUtil.onSave(transaction);
            budgetCalculator.calculate(budget, transaction.getDate());
            if (prevBudget != null){
                budgetCalculator.calculate(prevBudget, prevDate);
            }
        }
    }
}
