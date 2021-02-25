package utils.finances;

import entity.UserSystemCategory;
import entity.finance.accounts.Account;
import entity.finance.category.Category;
import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionType;
import utils.UserSystemCategoryUtil;
import utils.db.dao.daoService;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.savers.TransactionSaver;

import java.sql.Date;
import java.time.LocalDate;

public class AccountUtil {

    private final UserSystemCategoryUtil uscu = new UserSystemCategoryUtil();
    private final TransactionSaver saver = new TransactionSaver();

    public void updateSum(Account account, float sum, boolean isNew) {
        float amount = sum - account.getSum();
        System.out.println("Update sum " + amount + " for account '" + account.getTitle() + "'");
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
            saver.save(transaction);
        }
    }
}
