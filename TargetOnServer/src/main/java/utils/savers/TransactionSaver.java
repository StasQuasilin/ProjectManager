package utils.savers;

import entity.finance.category.Category;
import entity.finance.transactions.Transaction;
import utils.BuyListChecker;
import utils.db.dao.daoService;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.finances.TransactionUtil;

public class TransactionSaver {

    private final TransactionUtil transactionUtil = new TransactionUtil();
    private final TransactionDAO transactionDAO = daoService.getTransactionDAO();
    private final BuyListChecker buyListChecker = new BuyListChecker();

    public void save(Transaction transaction){
        transactionDAO.saveTransaction(transaction);
        transactionUtil.updateAccounts(transaction);
        buyListChecker.check(transaction);
    }
}
