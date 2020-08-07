package utils.db.dao.finance.transactions;

import entity.finance.category.Category;
import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionDetail;
import entity.user.User;
import utils.savers.TransactionSaver;

import java.util.Collection;
import java.util.List;

/**
 * Created by DELL on 09.07.2020.
 */
public interface TransactionDAO {
    List<Transaction> getUserTransactions(User user);
    Transaction getTransaction(Object id);
    void saveTransaction(Transaction transaction);

    List<Transaction> getTransactionsByCategory(Category category, int limit);

    List<TransactionDetail> getDetails(Object transactionId);

    void saveDetail(TransactionDetail detail);

    void removeDetails(Collection<TransactionDetail> values);
}
