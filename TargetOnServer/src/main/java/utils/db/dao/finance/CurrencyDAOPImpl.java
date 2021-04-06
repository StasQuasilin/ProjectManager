package utils.db.dao.finance;

import constants.Keys;
import entity.finance.Currency;
import entity.finance.UserCurrency;
import entity.user.User;
import utils.db.dao.finance.currency.CurrencyDAO;
import utils.db.hibernate.Hibernator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CurrencyDAOPImpl implements CurrencyDAO {

    private final Hibernator hibernator = Hibernator.getInstance();

    @Override
    public List<String> getUserCurrency(User user) {
        LinkedList<String> currency = new LinkedList<>();
        for (UserCurrency userCurrency : hibernator.query(UserCurrency.class, Keys.USER, user)){
            currency.add(userCurrency.getCurrency().getName());
        }
        return currency;
    }

    @Override
    public List<String> findCurrency(String key, User user) {
        final List<String> userCurrency = getUserCurrency(user);
        LinkedList<String> result = new LinkedList<>();
        for (Currency currency : hibernator.find(Currency.class, "name", key)){
            final String name = currency.getName();
            if (!userCurrency.contains(name)){
                result.add(name);
            }
        }
        return result;
    }

    @Override
    public UserCurrency getUserCurrency(Currency currency, User user) {
        final HashMap<String, Object> args = new HashMap<>();
        args.put(Keys.CURRENCY, currency);
        args.put(Keys.USER, user);
        return hibernator.get(UserCurrency.class, args);
    }

    @Override
    public Currency getCurrency(String name) {
        return hibernator.get(Currency.class, Keys.NAME, name);
    }

    @Override
    public void saveUserCurrency(UserCurrency userCurrency) {
        hibernator.save(userCurrency);
    }

    @Override
    public void checkUserCurrency(Currency currency, User user) {
        UserCurrency userCurrency = getUserCurrency(currency, user);
        if (userCurrency == null){
            userCurrency = new UserCurrency();
            userCurrency.setUser(user);
            userCurrency.setCurrency(currency);
            saveUserCurrency(userCurrency);
        }
    }
}
