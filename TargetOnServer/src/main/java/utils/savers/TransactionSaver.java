package utils.savers;

import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionDetail;
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
        updateAccounts(transaction);
//        buyListChecker.check(transaction);
    }

    public void updateAccounts(Transaction transaction){
        new Thread(() -> transactionUtil.updateAccounts(transaction)).start();
    }

    public void save(TransactionDetail detail) {
        transactionDAO.saveDetail(detail);
    }
}
