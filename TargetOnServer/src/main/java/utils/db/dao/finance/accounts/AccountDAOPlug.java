package utils.db.dao.finance.accounts;

import entity.finance.accounts.Account;
import entity.finance.accounts.AccountType;
import entity.user.User;
import subscribe.Subscribe;
import utils.Updater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by DELL on 07.07.2020.
 */
public class AccountDAOPlug implements AccountDAO {

    private final Updater updater = new Updater();

    static final HashMap<Integer, Account> accounts = new HashMap<>();
    static {
        save(createAccount("Card account", AccountType.card, "UAH", 8000, 1000));
        save(createAccount("Cash account", AccountType.cash, "UAH", 500, 0));
    }

    static Account createAccount(String title, AccountType type, String currency, int sum, int limit){
        Account account = new Account();
        account.setId(accounts.size() + 1);
        account.setTitle(title);
        account.setType(type);
        account.setCurrency(currency);
        account.setSum(sum);
        account.setLimit(limit);
        return account;
    }

    @Override
    public List<Account> getUserAccounts(User user) {
        return new ArrayList<>(accounts.values());
    }

    @Override
    public Account getAccount(Object id) {
        if (id != null){
            Integer integer = Integer.valueOf(String.valueOf(id));
            return accounts.getOrDefault(integer, null);
        }
        return null;
    }

    @Override
    public void saveAccount(Account account) {
        save(account);
        updater.update(Subscribe.accounts, account, account.getOwner());
    }

    static void save(Account account){
        if (account.getId() <= 0){
            account.setId(accounts.size() + 1);
        }
        accounts.put(account.getId(), account);
    }
}
