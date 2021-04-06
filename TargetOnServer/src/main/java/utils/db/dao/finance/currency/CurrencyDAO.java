package utils.db.dao.finance.currency;

import entity.finance.Currency;
import entity.finance.UserCurrency;
import entity.user.User;

import java.util.List;

/**
 * Created by DELL on 07.07.2020.
 */
public interface CurrencyDAO {
    List<String> getUserCurrency(User user);
    List<String> findCurrency(String key, User user);

    UserCurrency getUserCurrency(Currency currency, User user);

    Currency getCurrency(String name);

    void saveUserCurrency(UserCurrency userCurrency);

    void checkUserCurrency(Currency currency, User user);
}
