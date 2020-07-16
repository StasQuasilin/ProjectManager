package utils.finances;

import entity.finance.accounts.Account;
import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionPoint;

import java.sql.Date;

public class TransactionUtil {

    private final AccountPointUtil accountPointUtil = new AccountPointUtil();
    private final TransactionPointUtil transactionPointUtil = new TransactionPointUtil();

    public void updateAccounts(Transaction transaction) {
        final float amount = transaction.getAmount();
        if (amount != 0){
            final Account accountFrom = transaction.getAccountFrom();
            final Account accountTo = transaction.getAccountTo();
            if (accountFrom != null) {
                updatePoint(transaction, accountFrom, amount);
            }
            if (accountTo != null){
                updatePoint(transaction, accountFrom, -amount);
            }
        }
    }

    private void updatePoint(Transaction transaction, Account account, float amount) {
        final TransactionPoint point = transactionPointUtil.getPoint(transaction, account);
        point.setDate(transaction.getDate());
        point.setAmount(amount);
        point.setRate(transaction.getRate());
        point.setCurrency(transaction.getCurrency());
        transactionPointUtil.save(point);
        accountPointUtil.calculate(account.getId(), transaction.getDate());
        accountPointUtil.updateAccount(account);
    }

    public void removePoint(Transaction transaction, Account account){
        transactionPointUtil.removePoint(transaction, account);
        accountPointUtil.calculate(account.getId(), transaction.getDate());
        accountPointUtil.updateAccount(account);
    }

}
