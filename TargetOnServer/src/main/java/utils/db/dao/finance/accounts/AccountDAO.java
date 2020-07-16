package utils.db.dao.finance.accounts;

import entity.finance.accounts.Account;
import entity.user.User;

import java.util.List;

/**
 * Created by DELL on 07.07.2020.
 */
public interface AccountDAO {
    List<Account> getUserAccounts(User user);

    Account getAccount(Object id);

    void saveAccount(Account account);
}
