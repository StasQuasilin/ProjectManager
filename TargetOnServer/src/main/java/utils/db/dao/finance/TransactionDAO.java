package utils.db.dao.finance;

import entity.finance.Transaction;
import entity.user.User;

import java.util.List;

public interface TransactionDAO {
    List<Transaction> getLastTransactions(User owner);
    Transaction getTransaction(Object id);
    void saveTransaction(Transaction transaction);
}
