package utils.finances;

import entity.finance.accounts.Account;
import entity.finance.accounts.AccountPoint;
import entity.finance.accounts.PointScale;
import entity.finance.category.Category;
import entity.finance.category.Header;
import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionDetail;
import entity.finance.transactions.TransactionPoint;
import entity.finance.transactions.TransactionType;
import entity.task.TaskStatistic;
import entity.user.User;
import utils.db.hibernate.Hibernator;

import java.sql.Date;
import java.util.HashMap;

import static constants.Keys.*;

public class TransactionUtil {

    private final CategoryPointUtil categoryPointUtil = new CategoryPointUtil();
    private final AccountPointUtil accountPointUtil = new AccountPointUtil();
    private final TransactionPointUtil transactionPointUtil = new TransactionPointUtil();

    public void updateAccounts(Transaction transaction) {
        final float amount = transaction.getAmount();
        if (amount != 0){
            final float rate = transaction.getRate();
            final TransactionType type = transaction.getTransactionType();
//
            final Account accountFrom = transaction.getAccountFrom();
            if (accountFrom != null) {
                updatePoint(transaction, accountFrom, type != TransactionType.transfer ? -amount * rate: -amount);
            }
            final Account accountTo = transaction.getAccountTo();
            if (accountTo != null){
                updatePoint(transaction, accountTo, type != TransactionType.transfer ? amount / rate: amount);
            }
        }
    }

//    private void calculateCategory(Category category) {
//        float plus = 0;
//        float minus = 0;
////        for (AccountPoint point : accountPointUtil.getPoints(category.getId())){
////            plus += point.getPlus();
////            minus += point.getMinus();
////        }
//        final TaskStatistic statistic = categoryStatisticUtil.getStatistic(category);
//        statistic.setPlus(plus);
//        statistic.setMinus(minus);
//        hibernator.save(statistic);
//        categoryStatisticUtil.updateStatistic(category);
//    }

    private void updatePoint(Transaction transaction, Account account, float amount) {
        final TransactionPoint point = transactionPointUtil.getPoint(transaction, account);
//        final Date oldDate = point.getDate();
//        if (oldDate != null && !oldDate.equals(transaction.getDate())){
//            accountPointUtil.removeDay(oldDate, account);
//        }
        point.setDate(transaction.getDate());
        point.setAmount(amount);
        point.setRate(transaction.getRate());
        point.setCurrency(transaction.getCurrency().getName());
        transactionPointUtil.save(point);

        accountPointUtil.calculateDay(account, transaction.getDate());
    }

    public void removeTransactionPoint(Account account, int transaction, Date date) {
        transactionPointUtil.removePoint(account.getId(), transaction, date);
        accountPointUtil.calculateDay(account, date);
    }

    public void removePoint(Transaction transaction, Account account) {
        removeTransactionPoint(account, transaction.getId(), transaction.getDate());
    }

    public void updateCategoryDay(Header header, Date date) {
        categoryPointUtil.calculateDay(header, date);
    }
}
