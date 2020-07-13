package utils.db.dao;

import utils.db.dao.finance.accounts.AccountDAO;
import utils.db.dao.finance.accounts.AccountDAOPlug;
import utils.db.dao.finance.currency.CurrencyDAO;
import utils.db.dao.finance.currency.CurrencyDAOPlug;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.db.dao.finance.transactions.TransactionDAOPlug;
import utils.db.dao.goal.GaolDAOPlug;
import utils.db.dao.goal.GoalDAO;
import utils.db.dao.goal.GoalDAOImpl;

/**
 * Created by DELL on 07.07.2020.
 */
public class daoService {
    private static final AccountDAO accountDao = new AccountDAOPlug();
    private static final CurrencyDAO currencyDAO = new CurrencyDAOPlug();
    private static final TransactionDAO transactionDAO = new TransactionDAOPlug();
    private static final GoalDAO goalDAO = new GoalDAOImpl();

    public static AccountDAO getAccountDAO() {
        return accountDao;
    }

    public static CurrencyDAO getCurrencyDAO() {
        return currencyDAO;
    }

    public static TransactionDAO getTransactionDAO() {
        return transactionDAO;
    }

    public static GoalDAO getGoalDAO() {
        return goalDAO;
    }
}
