package utils.finances;

import entity.finance.accounts.Account;
import entity.finance.accounts.AccountPoint;
import entity.finance.accounts.PointScale;
import entity.finance.category.Category;
import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionPoint;
import entity.finance.transactions.TransactionType;
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
            final float rate = transaction.getRate();
            final TransactionType type = transaction.getTransactionType();

            final Account accountFrom = transaction.getAccountFrom();
            if (accountFrom != null) {
                updatePoint(transaction, accountFrom, type != TransactionType.transfer ? -amount * rate: -amount);
            }
            final Account accountTo = transaction.getAccountTo();
            if (accountTo != null){
                updatePoint(transaction, accountTo, type != TransactionType.transfer ? amount / rate: amount);
            }
            updateCategory(transaction.getCategory(), transaction.getDate());
        }
    }

    public void updateCategory(Category category, Date date) {
        if (!category.isHidden()) {
            final HashMap<String, Object> param = new HashMap<>();
            param.put(CATEGORY, category);
            param.put(DATE, date);

            float plus = 0;
            float minus = 0;
            for (Transaction t : hibernator.query(Transaction.class, param)) {
                float amount = t.getAmount();
                if (t.getTransactionType() == TransactionType.spending ){
                    amount *= -1;
                }
                if (amount > 0) {
                    plus += amount;
                } else {
                    minus += amount;
                }
            }
            final AccountPoint point = accountPointUtil.getPoint(category.getId(), date, PointScale.day);
            if (plus != 0 || minus != 0){
                point.setPlus(plus);
                point.setMinus(minus);
                hibernator.save(point);
            } else if(point.getId() > 0){
                hibernator.remove(point);
            }

            accountPointUtil.pointByPoint(category.getId(), date, PointScale.week);

            calculateCategory(category);
        }
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
//        final Date oldDate = point.getDate();
//        if (oldDate != null && !oldDate.equals(transaction.getDate())){
//            accountPointUtil.removeDay(oldDate, account);
//        }
        point.setDate(transaction.getDate());
        point.setAmount(amount);
        point.setRate(transaction.getRate());
        point.setCurrency(transaction.getCurrency());
        transactionPointUtil.save(point);

        calculateAccountDay(account, transaction.getDate());
        accountPointUtil.updateAccount(account);
    }

    public void calculateAccountDay(Account account, Date date){
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
        if (plus != 0 || minus != 0) {
            point.setPlus(plus);
            point.setMinus(minus);
            hibernator.save(point);
        } else if (point.getId() > 0){
            hibernator.remove(point);
        }

        accountPointUtil.pointByPoint(account.getId(), date, PointScale.week);
    }

    public void removeTransactionPoint(Account account, int transaction, Date date) {
        transactionPointUtil.removePoint(account.getId(), transaction, date);
        calculateAccountDay(account, date);
        accountPointUtil.updateAccount(account);
    }

    public void removePoint(Transaction transaction, Account account) {
        removeTransactionPoint(account, transaction.getId(), transaction.getDate());
    }
}
