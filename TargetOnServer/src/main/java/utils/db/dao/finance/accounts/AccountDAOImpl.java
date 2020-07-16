package utils.db.dao.finance.accounts;

import entity.finance.accounts.Account;
import entity.user.User;
import subscribe.Subscribe;
import utils.Updater;
import utils.db.hibernate.Hibernator;

import java.util.List;

import static constants.Keys.ID;
import static constants.Keys.OWNER;

public class AccountDAOImpl implements AccountDAO {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final Updater updater = new Updater();

    @Override
    public List<Account> getUserAccounts(User user) {
        return hibernator.query(Account.class, OWNER, user);
    }

    @Override
    public Account getAccount(Object id) {
        return hibernator.get(Account.class, ID, id);
    }

    @Override
    public void saveAccount(Account account) {
        hibernator.save(account);
        updater.update(Subscribe.accounts, account, account.getOwner());
    }
}
