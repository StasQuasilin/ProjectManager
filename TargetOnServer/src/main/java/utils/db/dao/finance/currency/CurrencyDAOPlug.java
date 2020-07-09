package utils.db.dao.finance.currency;

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
}
