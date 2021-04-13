package utils.db.dao.finance.transactions;

import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionDetail;
import entity.user.User;

import java.util.List;

/**
 * Created by DELL on 09.07.2020.
 */
public interface TransactionDAO {

    int LIMIT = 32;

    List<Transaction> getUserTransactions(User user);
    Transaction getTransaction(Object id);
    void saveTransaction(Transaction transaction);

    List<TransactionDetail> getDetails(Object transactionId);

    void saveDetail(TransactionDetail detail);

    void removeTransactions(User user);
}
