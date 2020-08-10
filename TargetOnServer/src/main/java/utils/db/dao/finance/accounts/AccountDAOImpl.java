package utils.db.dao.finance.accounts;

import entity.finance.accounts.Account;
import entity.finance.accounts.AccountMember;
import entity.finance.transactions.TransactionPoint;
import entity.user.User;
import subscribe.Subscribe;
import utils.Updater;
import utils.db.hibernate.Hibernator;

import java.util.List;

import static constants.Keys.*;

public class AccountDAOImpl implements AccountDAO {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final Updater updater = new Updater();

    @Override
    public List<Account> getUserAccounts(User user) {
        final List<Account> accounts = hibernator.query(Account.class, OWNER, user);
        for (AccountMember member : hibernator.query(AccountMember.class, USER, user)){
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
}
