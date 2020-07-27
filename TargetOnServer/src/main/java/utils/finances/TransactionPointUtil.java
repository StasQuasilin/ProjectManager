package utils.finances;

import entity.finance.accounts.Account;
import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionPoint;
import utils.db.hibernate.Hibernator;

import java.sql.Date;
import java.util.HashMap;

import static constants.Keys.*;

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

    public void removePoint(int accountId, int transaction, Date date) {
        params.clear();
        params.put(TRANSACTION, transaction);
        params.put(ACCOUNT, accountId);
        params.put(DATE, date);
        TransactionPoint point = hibernator.get(TransactionPoint.class, params);
        if (point != null){
            System.out.println("Remove transaction point for transaction " + transaction + ", account: " + accountId + ", date: " + date.toString());
            hibernator.remove(point);
        }
    }


}
