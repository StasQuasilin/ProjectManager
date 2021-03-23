package utils.removers;

import entity.finance.accounts.Account;
import entity.finance.transactions.Transaction;
import utils.Updater;
import utils.db.hibernate.Hibernator;
import utils.finances.CategoryPointUtil;
import utils.finances.PointUtil;
import utils.finances.TransactionPointUtil;
import utils.finances.TransactionUtil;

import java.sql.Date;

public class TransactionRemover {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final TransactionUtil transactionUtil = new TransactionUtil();
    private final TransactionPointUtil transactionPointUtil = new TransactionPointUtil();
    private final PointUtil accountPointUtil = new CategoryPointUtil();
    private final Updater updater = new Updater();

    public void removeTransaction(Transaction transaction){
        hibernator.remove(transaction);
//        updater.remove(Subscribe.transactions, transaction.getId(), transaction.getOwner());

        final Date date = transaction.getDate();

        final Account accountFrom = transaction.getAccountFrom();
        if (accountFrom != null){
            transactionUtil.removePoint(transaction, accountFrom);
        }
        final Account accountTo = transaction.getAccountTo();
        if (accountTo != null){
            transactionUtil.removePoint(transaction, accountTo);
        }
    }
}
