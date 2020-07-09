package utils.db.dao.finance.currency;

import entity.finance.Currency;
import entity.user.User;

import java.util.List;

/**
 * Created by DELL on 07.07.2020.
 */
public interface CurrencyDAO {
    List<String> getUserCurrency(User user);
}
