package utils.finances;

import entity.finance.accounts.Account;
import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionType;
import utils.UserSystemCategoryUtil;
import utils.db.dao.daoService;
import utils.db.dao.finance.currency.CurrencyDAO;
import utils.savers.TransactionSaver;

import java.sql.Date;
import java.time.LocalDate;

public class AccountUtil {

    private final UserSystemCategoryUtil uscu = new UserSystemCategoryUtil();
    private final TransactionSaver saver = new TransactionSaver();
    private final CurrencyDAO currencyDAO = daoService.getCurrencyDAO();

    public void updateSum(Account account, float sum, boolean isNew) {
        float amount = sum - account.getSum();
        System.out.println("Update sum " + amount + " for account '" + account.getHeader() + "'");
        if (amount != 0) {
            Transaction transaction = new Transaction();

            if (amount > 0){
                transaction.setTransactionType(TransactionType.income);
                transaction.setAccountTo(account);
            } else {
                transaction.setTransactionType(TransactionType.spending);
                transaction.setAccountFrom(account);
            }
            transaction.setDate(Date.valueOf(LocalDate.now()));
            transaction.setOwner(account.getOwner());
            transaction.setCurrency(currencyDAO.getCurrency(account.getCurrency()));
            transaction.setAmount(amount);
            saver.save(transaction);
        }
    }
}
