package utils.db.dao;

import utils.db.dao.finance.accounts.AccountDAO;
import utils.db.dao.finance.accounts.AccountDAOPlug;
import utils.db.dao.finance.currency.CurrencyDAO;
import utils.db.dao.finance.currency.CurrencyDAOPlug;

/**
 * Created by DELL on 07.07.2020.
 */
public class daoService {
    private static final AccountDAO accountDao = new AccountDAOPlug();
    private static final CurrencyDAO currencyDAO = new CurrencyDAOPlug();

    public static AccountDAO getAccountDAO() {
        return accountDao;
    }

    public static CurrencyDAO getCurrencyDAO() {
        return currencyDAO;
    }
}
