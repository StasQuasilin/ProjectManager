package utils.db.dao.finance.transactions;

import entity.finance.category.Category;
import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionDetail;
import entity.finance.transactions.TransactionPoint;
import entity.user.User;
import utils.Updater;
import utils.db.hibernate.Hibernator;

import java.util.Collection;
import java.util.List;

import static constants.Keys.*;

public class TransactionDAOImpl implements TransactionDAO {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final Updater updater = new Updater();

    @Override
    public List<Transaction> getUserTransactions(User user) {
        return hibernator.query(Transaction.class, TITLE_OWNER, user, LIMIT);
    }

    @Override
    public Transaction getTransaction(Object id) {
        return hibernator.get(Transaction.class, ID, id);
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        final Category category = transaction.getCategory();
        if (category != null) {
            hibernator.save();
        }
        hibernator.save(transaction);
//        updater.update(Subscribe.transactions, transaction, transaction.getOwner());

    }

    @Override
    public List<Transaction> getTransactionsByCategory(Category category, int limit) {
        return hibernator.query(Transaction.class, CATEGORY, category);
    }

    @Override
    public List<TransactionDetail> getDetails(Object transactionId) {
        return hibernator.query(TransactionDetail.class, TRANSACTION, transactionId);
    }

    @Override
    public void saveDetail(TransactionDetail detail) {
        hibernator.save(detail.getCategory());
        hibernator.save(detail);
    }

    @Override
    public void removeDetails(Collection<TransactionDetail> details) {
        for (TransactionDetail detail : details){
            removeDetail(detail);
        }
    }

    @Override
    public void removeTransactions(User user) {
        for (Transaction transaction : hibernator.query(Transaction.class, "category/owner", user)){
            hibernator.remove(TransactionPoint.class, "transaction", transaction.getId());
            hibernator.remove(transaction);
        }
    }

    private void removeDetail(TransactionDetail detail) {
        hibernator.remove(detail);
    }
}
