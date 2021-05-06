package utils.finances;

import entity.finance.accounts.Account;
import entity.finance.category.CategoryType;
import entity.finance.category.Header;
import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionDetail;
import entity.finance.transactions.TransactionType;
import entity.user.User;
import utils.UserSystemCategoryUtil;
import utils.categories.SystemCategoryUtil;
import utils.db.dao.daoService;
import utils.db.dao.finance.currency.CurrencyDAO;
import utils.savers.TransactionSaver;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class AccountUtil {

    private final UserSystemCategoryUtil uscu = new UserSystemCategoryUtil();
    private final TransactionSaver saver = new TransactionSaver();
    private final CurrencyDAO currencyDAO = daoService.getCurrencyDAO();
    private final SystemCategoryUtil systemCategoryUtil = new SystemCategoryUtil();
    private final TransactionNameUtil transactionNameUtil = new TransactionNameUtil();

    public void updateSum(Account account, float sum, boolean isNew, String language) {
        float amount = sum - account.getSum();
        if (amount != 0) {
            Transaction transaction = new Transaction();
            if (amount > 0){
                transaction.setTransactionType(TransactionType.income);
                transaction.setAccountTo(account);
            } else {
                transaction.setTransactionType(TransactionType.spending);
                transaction.setAccountFrom(account);
            }

            final User owner = account.getOwner();
            CategoryType type;
            if (isNew){
                type = CategoryType.new_account;
            } else {
                type = CategoryType.update_account;
            }
            final Header header = systemCategoryUtil.getHeader(type, language);
            TransactionDetail detail = new TransactionDetail();
            detail.setTransaction(transaction);
            detail.setHeader(header);
            detail.setPrice(Math.abs(amount));
            detail.setAmount(1);
            detail.setRate(1);

            transaction.setDate(Date.valueOf(LocalDate.now()));
            transaction.setOwner(owner);
            transaction.setCurrency(currencyDAO.getCurrency(account.getCurrency()));
//            transaction.setAmount(amount);

            LinkedList<TransactionDetail> details = new LinkedList<>();
            details.push(detail);

            transactionNameUtil.updateTransactionName(transaction, details, new LinkedList<>());

            saver.save(transaction);
        }
    }
}
