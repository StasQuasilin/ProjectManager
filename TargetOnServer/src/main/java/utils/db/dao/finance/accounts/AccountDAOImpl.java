package utils.db.dao.finance.accounts;

import entity.finance.accounts.Account;
import entity.finance.accounts.AccountMember;
import entity.finance.accounts.DepositSettings;
import entity.finance.transactions.TransactionPoint;
import entity.user.User;
import subscribe.Subscribe;
import utils.Updater;
import utils.db.hibernate.Hibernator;

import java.util.HashMap;
import java.util.List;

import static constants.Keys.*;

public class AccountDAOImpl implements AccountDAO {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final Updater updater = new Updater();

    @Override
    public List<Account> getUserAccounts(User user) {
        final List<Account> accounts = hibernator.query(Account.class, OWNER, user);
        for (AccountMember member : hibernator.query(AccountMember.class, MEMBER, user)){
            accounts.add(member.getAccount());
        }
        return accounts;
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

    @Override
    public List<TransactionPoint> getAccountPoints(Object o) {
        return hibernator.query(TransactionPoint.class, ACCOUNT, o);
    }

    @Override
    public DepositSettings getDepositSettings(Account account) {
        return hibernator.get(DepositSettings.class, ACCOUNT, account);
    }

    @Override
    public List<Account> getVisibleAccounts(User user) {
        final HashMap<String, Object> param = new HashMap<>();
        param.put(OWNER, user);
        param.put(SHOW, true);
        return hibernator.query(Account.class, param);
    }

    @Override
    public void removeSettings(DepositSettings depositSettings) {
        hibernator.remove(depositSettings);
    }

    @Override
    public void saveDepositSettings(DepositSettings depositSettings) {
        hibernator.save(depositSettings);
    }

    @Override
    public void removeAccounts(User user) {
        hibernator.remove(Account.class, "owner", user);
    }

    @Override
    public List<AccountMember> getMembers(Object account) {
        return hibernator.query(AccountMember.class, "account", account);
    }

    @Override
    public void saveMember(AccountMember member) {
        hibernator.save(member);
    }

    @Override
    public void removeMember(AccountMember member) {
        hibernator.remove(member);
    }
}
