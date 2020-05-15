package api.budget;

import api.socket.UpdateUtil;
import constants.API;
import controllers.ServletAPI;
import entity.accounts.*;
import entity.transactions.Transaction;
import entity.transactions.TransactionType;
import entity.user.User;
import entity.user.UserSettings;
import org.json.simple.JSONObject;
import utils.BudgetCalculator;
import utils.UserSettingsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by szpt_user045 on 27.02.2020.
 */
@WebServlet(API.BUDGET_EDIT)
public class AccountEditAPI extends ServletAPI {

    private final UpdateUtil updateUtil = new UpdateUtil();
    private final BudgetCalculator budgetCalculator = new BudgetCalculator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            User user = getUser(req);
            Account account = dao.getObjectById(Account.class, body.get(ID));
            boolean newAccount = false;
            boolean rename = false;
            if (account == null){
                newAccount = true;
                account = new Account();
                account.setOwner(user);
                account.setCreate(Timestamp.valueOf(LocalDateTime.now()));
                account.setBudgetSize(BudgetSize.floated);
            }

            String title = String.valueOf(body.get(TITLE));
            if (account.getTitle() == null || !account.getTitle().equals(title)){
                account.setTitle(title);
                rename = true;
            }

            float limit = Float.parseFloat(String.valueOf(body.get(LIMIT)));
            account.setLimit(limit);

            AccountType accountType = AccountType.valueOf(String.valueOf(body.get(TYPE)));
            account.setAccountType(accountType);

            account.setCurrency(dao.getObjectById(Currency.class, body.get(CURRENCY)));

            write(resp, SUCCESS);
            dao.save(account);
            updateUtil.onSave(account);

            float amount = Float.parseFloat(String.valueOf(body.get(AMOUNT)));
            if (account.getBudgetSum() != amount){
                float d = amount - account.getBudgetSum();
                Transaction transaction = new Transaction();
                transaction.setAccount(account);
                transaction.setAmount(d);
                Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
                transaction.setDateTime(timestamp);

                transaction.setType(d > 0 ? TransactionType.income : TransactionType.outcome);
                transaction.setOwner(user);
                transaction.setCurrency(account.getCurrency());
                UserSettings settings = UserSettingsUtil.getUserSettings(user);

                if (newAccount){
                    transaction.setCategory(settings.getOpenCategory());
                } else {
                    transaction.setCategory(settings.getCorrectionCategory());
                }

                dao.save(transaction);
                updateUtil.onSave(transaction);
                budgetCalculator.calculatePointRoot(transaction);

            }

            DepositSettings settings = dao.getDepositSettingsByAccount(account);
            if (account.getAccountType() == AccountType.deposit){
                if (settings == null){
                    settings = new DepositSettings();
                    settings.setAccount(account);
                }
                JSONObject o = (JSONObject) body.get(SETTINGS);
                Date open = Date.valueOf(String.valueOf(o.get(OPEN)));
                settings.setOpen(open);
                Date close = Date.valueOf(String.valueOf(o.get(CLOSE)));
                settings.setClose(close);
                float bid = Float.parseFloat(String.valueOf(o.get(BID)));
                settings.setBid(bid);
                int payment = Integer.parseInt(String.valueOf(o.get(PAYMENT)));
                settings.setPaymentPeriod(payment);
                int paymentAccountId = Integer.parseInt(String.valueOf(o.get(ACCOUNT)));
                if (paymentAccountId == 0){
                    settings.setPaymentAccount(account);
                } else {
                    Account paymentAccount = dao.getObjectById(Account.class, paymentAccountId);
                    settings.setPaymentAccount(paymentAccount);
                }
                dao.save(settings);
            } else if (settings != null){
                dao.remove(settings);
            }
        }
    }
}
