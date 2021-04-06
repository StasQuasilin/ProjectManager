package utils.db.dao.finance.currency;

import entity.finance.Currency;
import entity.finance.UserCurrency;
import entity.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 07.07.2020.
 */
public class CurrencyDAOPlug implements CurrencyDAO {

    static final List<String> currency = new ArrayList<>();
    static {
        currency.add("UAH");
        currency.add("USD");
    }
    @Override
    public List<String> getUserCurrency(User user) {
        return currency;
    }

    @Override
    public List<String> findCurrency(String key, User user) {
        return null;
    }

    @Override
    public UserCurrency getUserCurrency(Currency currency, User user) {
        return null;
    }

    @Override
    public Currency getCurrency(String name) {
        return null;
    }

    @Override
    public void saveUserCurrency(UserCurrency userCurrency) {

    }

    @Override
    public void checkUserCurrency(Currency currency, User user) {

    }
}
