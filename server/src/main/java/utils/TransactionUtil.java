package utils;

import constants.Keys;
import entity.accounts.Account;
import entity.accounts.Counterparty;
import entity.accounts.Currency;
import entity.transactions.Transaction;
import entity.transactions.TransactionCategory;
import entity.transactions.TransactionType;
import entity.user.User;
import org.json.simple.JSONObject;
import services.hibernate.dbDAO;
import services.hibernate.dbDAOService;

import java.sql.Date;

import static constants.Keys.*;

public class TransactionUtil {

    private static final String TZ_REGEX = "[TZ]";
    public final dbDAO dao = dbDAOService.getDao();
    private final CategoryUtil categoryUtil = new CategoryUtil();
    private final CounterpartyUtil counterpartyUtil = new CounterpartyUtil();

    public void filTransaction(Transaction transaction, JSONObject body, User user){

        if (body.containsKey(DATE)) {
            String s = String.valueOf(body.get(DATE)).replaceAll(TZ_REGEX, SPACE);
            Date date = Date.valueOf(s);
            transaction.setDate(date);
        }

        Account account = dao.getObjectById(Account.class, body.get(ACCOUNT));
        transaction.setAccount(account);

        if (body.containsKey(CATEGORY)) {
            TransactionCategory category = categoryUtil.getCategory((JSONObject) body.get(CATEGORY), user);
            transaction.setCategory(category);
        }

        TransactionType type = TransactionType.valueOf(String.valueOf(body.get(TYPE)));
        transaction.setType(type);

        if(body.containsKey(SECONDARY)) {
            Account secondary = dao.getObjectById(Account.class, body.get(SECONDARY));
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

    }

    public Transaction copy(Transaction transaction) {
        Transaction t = new Transaction();
        t.setType(transaction.getType());
        t.setAccount(transaction.getAccount());
        t.setSecondary(transaction.getSecondary());
        t.setCategory(transaction.getCategory());
        t.setAmount(transaction.getAmount());
        t.setRate(transaction.getRate());
        t.setCurrency(transaction.getCurrency());
        t.setCounterparty(transaction.getCounterparty());
        return t;
    }
}
