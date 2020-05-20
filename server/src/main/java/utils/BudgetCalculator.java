package utils;

import api.socket.UpdateUtil;
import entity.accounts.Account;
import entity.accounts.AccountPoint;
import entity.accounts.PointRoot;
import entity.accounts.PointScale;
import entity.transactions.Transaction;
import services.hibernate.dbDAO;
import services.hibernate.dbDAOService;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by szpt_user045 on 27.02.2020.
 */
public class BudgetCalculator extends Calculator {

    dbDAO dao = dbDAOService.getDao();
    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    public void afterBurn(int accountId) {
        float amount = 0;
        for (AccountPoint point : dao.getAccountPoints(null, null, accountId, PointScale.year)){
            amount += point.getQuantity();
        }
        Account account = dao.getObjectById(Account.class, accountId);
        if (account.getBudgetSum() != amount) {
            account.setBudgetSum(amount);
            dao.save(account);
            try {
                updateUtil.onSave(account);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void calculatePointRoot(Transaction t) {
        if(t.getAccount() != null) {
            calculatePointRoot(t.getId(), t.getDate(), t.getAccount().getId(), t.getTotalSum());
        }
        if (t.getSecondary() != null){
            calculatePointRoot(t.getId(), t.getDate(), t.getSecondary().getId(), t.getTotalSum() * -1);
        }
    }
}
