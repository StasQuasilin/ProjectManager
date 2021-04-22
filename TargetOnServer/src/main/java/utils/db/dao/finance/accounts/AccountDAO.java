package utils.db.dao.finance.accounts;

import entity.finance.accounts.Account;
import entity.finance.accounts.AccountMember;
import entity.finance.accounts.CardSettings;
import entity.finance.accounts.DepositSettings;
import entity.finance.category.Header;
import entity.finance.transactions.TransactionPoint;
import entity.user.User;

import java.util.List;

/**
 * Created by DELL on 07.07.2020.
 */
public interface AccountDAO {
    List<Account> getUserAccounts(User user, boolean all);

    Account getAccount(Object id);

    void saveAccount(Account account);

    List<TransactionPoint> getAccountPoints(Object o);

    DepositSettings getDepositSettings(Account account);

    List<Account> getVisibleAccounts(User user);

    void removeSettings(DepositSettings depositSettings);

    void saveDepositSettings(DepositSettings depositSettings);

    void removeAccounts(User user);

    List<AccountMember> getMembers(Object account);

    void saveMember(AccountMember member);

    void removeMember(AccountMember member);

    CardSettings getCardSettings(Account account);

    void saveCardSettings(CardSettings cardSettings);

    void removeSettings(CardSettings cardSettings);

    Account getAccountByHeader(Header header);
}
