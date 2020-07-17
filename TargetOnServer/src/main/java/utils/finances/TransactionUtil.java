package utils.finances;

import entity.finance.accounts.Account;
import entity.finance.accounts.AccountPoint;
import entity.finance.accounts.PointScale;
import entity.finance.category.Category;
import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionPoint;
import entity.task.TaskStatistic;
import utils.db.hibernate.Hibernator;

import java.sql.Date;
import java.util.HashMap;

import static constants.Keys.*;

public class TransactionUtil {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final AccountPointUtil accountPointUtil = new AccountPointUtil();
    private final TransactionPointUtil transactionPointUtil = new TransactionPointUtil();
    private final CategoryStatisticUtil categoryStatisticUtil = new CategoryStatisticUtil();

    public void updateAccounts(Transaction transaction) {
        final float amount = transaction.getAmount();
        if (amount != 0){
            final Account accountFrom = transaction.getAccountFrom();
            final Account accountTo = transaction.getAccountTo();
            if (accountFrom != null) {
                updatePoint(transaction, accountFrom, amount);
            }
            if (accountTo != null){
                updatePoint(transaction, accountFrom, -amount);
            }
            updateCategory(transaction.getCategory(), transaction.getDate());
        }
    }

    public void updateCategory(Category category, Date date) {
        final HashMap<String, Object> param = new HashMap<>();
        param.put(CATEGORY, category);
        param.put(DATE, date);

        float plus = 0;
        float minus = 0;
        for (Transaction t : hibernator.query(Transaction.class, param)){
            final float amount = t.getAmount();
            if (amount > 0){
                plus += amount;
            } else {
                minus += amount;
            }
        }
        AccountPoint point = accountPointUtil.getPoint(category.getId(), date, PointScale.day);
        point.setPlus(plus);
        point.setMinus(minus);
        hibernator.save(point);
        accountPointUtil.pointByPoint(category.getId(), date, PointScale.week);

        calculateCategory(category);
    }

    private void calculateCategory(Category category) {
        float plus = 0;
        float minus = 0;
        for (AccountPoint point : accountPointUtil.getPoints(category.getId())){
            plus += point.getPlus();
            minus += point.getMinus();
        }
        final TaskStatistic statistic = categoryStatisticUtil.getStatistic(category);
        statistic.setPlus(plus);
        statistic.setMinus(minus);
        hibernator.save(statistic);
        categoryStatisticUtil.updateStatistic(category);
    }

    private void updatePoint(Transaction transaction, Account account, float amount) {
        final TransactionPoint point = transactionPointUtil.getPoint(transaction, account);
        point.setDate(transaction.getDate());
        point.setAmount(amount);
        point.setRate(transaction.getRate());
        point.setCurrency(transaction.getCurrency());
        transactionPointUtil.save(point);

        calculateAccountDay(account, transaction.getDate());
        accountPointUtil.updateAccount(account);
    }

    private void calculateAccountDay(Account account, Date date){
        final HashMap<String, Object> param = new HashMap<>();
        param.put(ACCOUNT, account.getId());
        param.put(DATE, date);
        float plus = 0;
        float minus = 0;
        for (TransactionPoint p : hibernator.query(TransactionPoint.class, param)){
            final float amount = p.getAmount();
            if (amount > 0){
                plus += amount;
            } else {
                minus += amount;
            }
        }
        AccountPoint point = accountPointUtil.getPoint(account.getId(), date, PointScale.day);
        point.setPlus(plus);
        point.setMinus(minus);
        hibernator.save(point);
        accountPointUtil.pointByPoint(account.getId(), date, PointScale.week);
    }

    public void removePoint(Transaction transaction, Account account){
        transactionPointUtil.removePoint(transaction, account);
        calculateAccountDay(account, transaction.getDate());
        accountPointUtil.updateAccount(account);
    }
}
