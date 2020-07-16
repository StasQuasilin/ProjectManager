package utils.db.dao;

import utils.db.dao.calendar.CalendarDAO;
import utils.db.dao.calendar.CalendarDAOImpl;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.category.CategoryDAOImpl;
import utils.db.dao.category.CategoryDAOPlug;
import utils.db.dao.finance.accounts.AccountDAO;
import utils.db.dao.finance.accounts.AccountDAOImpl;
import utils.db.dao.finance.accounts.AccountDAOPlug;
import utils.db.dao.finance.buy.BuyListDAO;
import utils.db.dao.finance.buy.BuyListDAOImpl;
import utils.db.dao.finance.currency.CurrencyDAO;
import utils.db.dao.finance.currency.CurrencyDAOPlug;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.db.dao.finance.transactions.TransactionDAOImpl;
import utils.db.dao.finance.transactions.TransactionDAOPlug;
import utils.db.dao.goal.GaolDAOPlug;
import utils.db.dao.goal.GoalDAO;
import utils.db.dao.goal.GoalDAOImpl;
import utils.db.dao.tree.TaskDAO;
import utils.db.dao.tree.TaskDAOImpl;
import utils.db.dao.tree.TaskDAOPlug;

/**
 * Created by DELL on 07.07.2020.
 */
public class daoService {
    private static final AccountDAO     accountDao = new AccountDAOImpl();
    private static final CurrencyDAO    currencyDAO = new CurrencyDAOPlug();
    private static final TransactionDAO transactionDAO = new TransactionDAOImpl();
    private static final GoalDAO        goalDAO = new GoalDAOImpl();
    private static final TaskDAO        taskDAO = new TaskDAOImpl();
    private static final CategoryDAO    categoryDAO = new CategoryDAOImpl();
    private static final CalendarDAO    calendarDAO = new CalendarDAOImpl();
    private static final BuyListDAO     buyListDAO = new BuyListDAOImpl();

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

    public static TaskDAO getTaskDAO() {
        return taskDAO;
    }

    public static CategoryDAO getCategoryDAO() {
        return categoryDAO;
    }

    public static CalendarDAO getCalendarDAO() {
        return calendarDAO;
    }

    public static BuyListDAO getBuyListDAO() {
        return buyListDAO;
    }
}
