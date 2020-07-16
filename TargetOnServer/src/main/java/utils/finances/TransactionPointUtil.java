package utils.finances;

import entity.finance.accounts.Account;
import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionPoint;
import utils.db.hibernate.Hibernator;

import java.util.HashMap;

import static constants.Keys.ACCOUNT;
import static constants.Keys.TRANSACTION;

public class TransactionPointUtil {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final HashMap<String, Object> params = new HashMap<>();

    public TransactionPoint getPoint(Transaction transaction, Account account){
        params.clear();
        params.put(TRANSACTION, transaction.getId());
        params.put(ACCOUNT, account.getId());
        TransactionPoint point = hibernator.get(TransactionPoint.class, params);
        if (point == null){
            point = new TransactionPoint();
            point.setTransaction(transaction.getId());
            point.setAccount(account.getId());
        }
        return point;
    }

    public void save(TransactionPoint point) {
        hibernator.save(point);
    }

    public void removePoint(Transaction transaction, Account account) {
        params.clear();
        params.put(TRANSACTION, transaction.getId());
        params.put(ACCOUNT, account.getId());
        TransactionPoint point = hibernator.get(TransactionPoint.class, params);
        if (point != null){
            hibernator.remove(point);
        }
    }
}
