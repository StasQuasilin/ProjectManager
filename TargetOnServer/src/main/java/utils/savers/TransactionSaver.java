package utils.savers;

import entity.finance.transactions.Transaction;
import utils.db.dao.daoService;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.finances.TransactionUtil;

public class TransactionSaver {

    private final TransactionUtil transactionUtil = new TransactionUtil();
    private final TransactionDAO transactionDAO = daoService.getTransactionDAO();

    public void save(Transaction transaction){
        transactionDAO.saveTransaction(transaction);
        transactionUtil.updateAccounts(transaction);
    }
}
