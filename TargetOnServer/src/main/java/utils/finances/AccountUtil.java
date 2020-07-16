package utils.finances;

import entity.UserSystemCategory;
import entity.finance.accounts.Account;
import entity.finance.transactions.Category;
import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionType;
import entity.user.User;
import utils.UserSystemCategoryUtil;
import utils.db.dao.daoService;
import utils.db.dao.finance.transactions.TransactionDAO;

import java.sql.Date;
import java.time.LocalDate;

public class AccountUtil {

    private final UserSystemCategoryUtil uscu = new UserSystemCategoryUtil();
    private final TransactionDAO transactionDAO = daoService.getTransactionDAO();

    public void updateSum(Account account, float sum, boolean isNew) {
        float amount = sum - account.getSum();
        if (amount != 0) {
            Transaction transaction = new Transaction();
            final UserSystemCategory cat = uscu.getCategories(account.getOwner());
            Category category = isNew ? cat.getCreateAccount() : cat.getAccountCorrection();
            transaction.setCategory(category);
            transaction.setAccountFrom(account);

            if (amount > 0){
                transaction.setTransactionType(TransactionType.income);
            } else {
                transaction.setTransactionType(TransactionType.spending);
            }
            transaction.setDate(Date.valueOf(LocalDate.now()));
            transaction.setCurrency(account.getCurrency());
            transaction.setRate(1);
            transactionDAO.saveTransaction(transaction);
        }
    }
}
