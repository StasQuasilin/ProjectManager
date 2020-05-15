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

    private static final String TZ_REGEX = "[TZ]";
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
            String s = String.valueOf(body.get(DATE)).replaceAll(TZ_REGEX, SPACE);
            Timestamp timestamp = Timestamp.valueOf(s);
            Date otherDate;
            if (transaction.getDateTime() != null){
                otherDate = transaction.getDate();
                transaction.setDateTime(timestamp);
            } else {
                transaction.setDateTime(timestamp);
                otherDate = transaction.getDate();
            }

            Account account = dao.getObjectById(Account.class, body.get(ACCOUNT));
            if (transaction.getAccount() != null && transaction.getAccount().getId() != account.getId()){
                if (transaction.getId() > 0){
                    budgetCalculator.removePointRoot(transaction.getId(), transaction.getAccount(), otherDate);
                }
            }
            transaction.setAccount(account);

            if (body.containsKey(CATEGORY)) {
                TransactionCategory category = categoryUtil.getCategory((JSONObject) body.get(CATEGORY), user);
                transaction.setCategory(category);
            }

            TransactionType type = TransactionType.valueOf(String.valueOf(body.get(TYPE)));
            transaction.setType(type);

            if (type == TransactionType.transfer){
                Account secondary = dao.getObjectById(Account.class, body.get(SECONDARY));
                if (transaction.getSecondary() != null && transaction.getSecondary().getId() != secondary.getId()){
                    if (transaction.getId() > 0) {
                        budgetCalculator.removePointRoot(transaction.getId(), transaction.getSecondary(), otherDate);
                    }
                }
                transaction.setSecondary(secondary);
            }

            float sum = Float.parseFloat(String.valueOf(body.get(SUM)));
            if (type == TransactionType.outcome || type == TransactionType.transfer){
                sum *= -1;
            }
            transaction.setAmount(sum);

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

            dao.save(transaction);
            write(resp, SUCCESS);
            updateUtil.onSave(transaction);
            budgetCalculator.calculatePointRoot(transaction);
        }
    }
}
