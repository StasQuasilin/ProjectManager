package utils.finances;

import entity.finance.accounts.Account;
import entity.finance.accounts.PointType;
import entity.finance.transactions.TransactionPoint;
import entity.user.User;
import utils.db.dao.daoService;
import utils.db.dao.finance.accounts.AccountDAO;

import java.sql.Date;
import java.util.HashMap;

import static constants.Keys.ACCOUNT;
import static constants.Keys.DATE;

public class AccountPointUtil extends PointUtil<Account> {

    private final AccountDAO accountDAO = daoService.getAccountDAO();

    @Override
    public PointType getType() {
        return PointType.account;
    }

    @Override
    public int getAccountId(Account acc) {
        return acc.getId();
    }

    @Override
    public User getAccountOwner(Account acc) {
        return acc.getOwner();
    }

    @Override
    public PlusMinus calculateAccountDay(Account acc, Date date) {
        final HashMap<String, Object> param = new HashMap<>();
        param.put(ACCOUNT, acc.getId());
        param.put(DATE, date);

        PlusMinus plusMinus = new PlusMinus();
        for (TransactionPoint p : hibernator.query(TransactionPoint.class, param)){
            final float amount = p.getAmount();
            if (amount > 0){
                plusMinus.plus += amount;
            } else {
                plusMinus.minus += amount;
            }
        }
        return plusMinus;
    }

    @Override
    protected void updateAccount(Account acc, Date date) {
        final PlusMinus byYear = getTotalByYear(acc);
        acc.setSum(byYear.plus + byYear.minus);
        accountDAO.saveAccount(acc);
    }
}
