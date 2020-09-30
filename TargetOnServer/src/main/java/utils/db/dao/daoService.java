package utils.db.dao;

import utils.db.dao.access.DemoAccessDAO;
import utils.db.dao.access.DemoAccessDAOImpl;
import utils.db.dao.access.UserAccessDAO;
import utils.db.dao.access.UserAccessDAOImpl;
import utils.db.dao.calendar.CalendarDAO;
import utils.db.dao.calendar.CalendarDAOImpl;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.category.CategoryDAOImpl;
import utils.db.dao.finance.accounts.AccountDAO;
import utils.db.dao.finance.accounts.AccountDAOImpl;
import utils.db.dao.finance.buy.BuyListDAO;
import utils.db.dao.finance.buy.BuyListDAOImpl;
import utils.db.dao.finance.currency.CurrencyDAO;
import utils.db.dao.finance.currency.CurrencyDAOPlug;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.db.dao.finance.transactions.TransactionDAOImpl;
import utils.db.dao.goal.GoalDAO;
import utils.db.dao.goal.GoalDAOImpl;
import utils.db.dao.tree.TaskDAO;
import utils.db.dao.tree.TaskDAOImpl;
import utils.db.dao.user.FriendshipDAO;
import utils.db.dao.user.FriendshipDAOHibernate;
import utils.db.dao.user.UserDAO;
import utils.db.dao.user.UserDAOImpl;

/**
 * Created by DELL on 07.07.2020.
 */
public class daoService {
    private static final AccountDAO     accountDao;
    private static final CurrencyDAO    currencyDAO = new CurrencyDAOPlug();
    private static final TransactionDAO transactionDAO = new TransactionDAOImpl();
    private static final GoalDAO        goalDAO = new GoalDAOImpl();
    private static final CategoryDAO    categoryDAO;
    private static final TaskDAO        taskDAO;
    private static final CalendarDAO    calendarDAO = new CalendarDAOImpl();
    private static final BuyListDAO     buyListDAO = new BuyListDAOImpl();
    private static final UserAccessDAO  userAccessDAO = new UserAccessDAOImpl();
    private static final UserDAO        userDAO = new UserDAOImpl();
    private static final DemoAccessDAO  demoDAO = new DemoAccessDAOImpl();
    private static final FriendshipDAO friendshipDAO = new FriendshipDAOHibernate();
    static {
        accountDao = new AccountDAOImpl();
        categoryDAO = new CategoryDAOImpl();
        taskDAO = new TaskDAOImpl();
    }

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

    public static UserAccessDAO getUserAccessDAO() {
        return userAccessDAO;
    }

    public static UserDAO getUserDAO() {
        return userDAO;
    }

    public static DemoAccessDAO getDemoDAO() {
        return demoDAO;
    }

    public static FriendshipDAO getFriendshipDAO() {
        return friendshipDAO;
    }
}
