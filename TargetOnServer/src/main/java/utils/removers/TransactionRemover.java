package utils.removers;

import entity.finance.accounts.Account;
import entity.finance.accounts.PointScale;
import entity.finance.transactions.Transaction;
import subscribe.Subscribe;
import utils.Updater;
import utils.db.hibernate.Hibernator;
import utils.finances.AccountPointUtil;
import utils.finances.TransactionPointUtil;
import utils.finances.TransactionUtil;

import java.sql.Date;

public class TransactionRemover {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final TransactionUtil transactionUtil = new TransactionUtil();
    private final TransactionPointUtil transactionPointUtil = new TransactionPointUtil();
    private final AccountPointUtil accountPointUtil = new AccountPointUtil();
    private final Updater updater = new Updater();

    public void removeTransaction(Transaction transaction){
        hibernator.remove(transaction);
        updater.remove(Subscribe.transactions, transaction.getId(), transaction.getOwner());
        final Date date = transaction.getDate();

        transactionUtil.removePoint(transaction.getCategory(), date);

        final Account accountFrom = transaction.getAccountFrom();
        if (accountFrom != null){
            transactionPointUtil.removePoint(transaction, accountFrom);
            accountPointUtil.removeDay(date, accountFrom);
            accountPointUtil.pointByPoint(accountFrom.getId(), date, PointScale.week);
            accountPointUtil.updateAccount(accountFrom);
        }
        final Account accountTo = transaction.getAccountTo();
        if (accountTo != null){
            transactionPointUtil.removePoint(transaction, accountTo);
            accountPointUtil.removeDay(date, accountTo);
            accountPointUtil.pointByPoint(accountTo.getId(), date, PointScale.week);
            accountPointUtil.updateAccount(accountTo);
        }
    }
}
