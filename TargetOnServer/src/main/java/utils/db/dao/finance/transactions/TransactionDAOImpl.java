package utils.db.dao.finance.transactions;

import entity.finance.transactions.Transaction;
import entity.user.User;
import subscribe.Subscribe;
import utils.Updater;
import utils.db.hibernate.Hibernator;
import utils.finances.TransactionUtil;

import java.util.List;

import static constants.Keys.ID;
import static constants.Keys.TASK_OWNER;

public class TransactionDAOImpl implements TransactionDAO {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final Updater updater = new Updater();
    private final TransactionUtil transactionUtil = new TransactionUtil();

    @Override
    public List<Transaction> getUserTransactions(User user) {
        return hibernator.query(Transaction.class, TASK_OWNER, user);
    }

    @Override
    public Transaction getTransaction(Object id) {
        return hibernator.get(Transaction.class, ID, id);
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        hibernator.save(transaction.getCategory());
        hibernator.save(transaction);
        updater.update(Subscribe.transactions, transaction, transaction.getOwner());
        transactionUtil.updateAccounts(transaction);
    }
}
