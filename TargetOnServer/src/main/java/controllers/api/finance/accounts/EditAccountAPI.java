package controllers.api.finance.accounts;

import constants.ApiLinks;
import controllers.api.API;
import entity.finance.Currency;
import entity.finance.accounts.Account;
import entity.finance.accounts.AccountType;
import entity.finance.accounts.CardSettings;
import entity.finance.accounts.DepositSettings;
import entity.finance.category.Header;
import entity.finance.category.HeaderType;
import entity.user.User;
import utils.answers.Answer;
import utils.db.dao.daoService;
import utils.db.dao.finance.accounts.AccountDAO;
import utils.db.dao.finance.currency.CurrencyDAO;
import utils.finances.AccountUtil;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(ApiLinks.ACCOUNT_EDIT)
public class EditAccountAPI extends API {

    private final AccountDAO accountDAO = daoService.getAccountDAO();
    private final AccountUtil accountUtil = new AccountUtil();
    private final CurrencyDAO currencyDAO = daoService.getCurrencyDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject body = parseBody(req);
        Answer answer;
        if (body != null){
            System.out.println(body);
            User user = getUser(req);
            Account account = accountDAO.getAccount(body.get(ID));
            boolean newAccount = false;

            if(account == null){
                account = new Account();
                Header title = new Header();
                title.setType(HeaderType.account);
                title.setOwner(user);
                account.setHeader(title);
                newAccount = true;
            }

            final Header title = account.getHeader();
            title.setValue(body.getString(TITLE));
            AccountType type = AccountType.valueOf(body.getString(TYPE));
            account.setType(type);

            if(body.containKey(CURRENCY)){
                final String currencyName = body.getString(CURRENCY);
                final Currency currency = currencyDAO.getCurrency(currencyName);
                account.setCurrency(currency.getName());
                currencyDAO.checkUserCurrency(currency, user);
            }

            int limit = body.getInt(LIMIT);
            if (type != AccountType.card && type != AccountType.credit){
                limit = 0;
            }
            account.setLimit(limit);

            final boolean show = body.getBoolean(SHOW);
            account.setShow(show);

            accountDAO.saveAccount(account);

            DepositSettings depositSettings = accountDAO.getDepositSettings(account);
            if (type == AccountType.deposit){
                if (depositSettings == null){
                    depositSettings = new DepositSettings();
                    depositSettings.setAccount(account);
                }
                final JsonObject settings = new JsonObject(body.get(SETTINGS));
                depositSettings.setOpen(settings.getDate(OPEN));
                depositSettings.setPeriod(settings.getLong(PERIOD));
                depositSettings.setRate(settings.getFloat(RATE));
                depositSettings.setCapitalization(settings.getBoolean(CAPITALIZATION));
                accountDAO.saveDepositSettings(depositSettings);

            } else {
                if (depositSettings != null){
                    accountDAO.removeSettings(depositSettings);
                }
            }

            CardSettings cardSettings = accountDAO.getCardSettings(account);
            if (type == AccountType.card){
                if (cardSettings == null){
                    cardSettings = new CardSettings();
                    cardSettings.setAccount(account);
                }
                final JsonObject settings = new JsonObject(body.get(SETTINGS));
                cardSettings.setExemption(settings.getInt(EXEMPTION));
                cardSettings.setRemember(settings.getBoolean(REMEMBER));
                accountDAO.saveCardSettings(cardSettings);
            } else if (cardSettings != null){
                accountDAO.removeSettings(cardSettings);
            }

            final float sum = body.getFloat(SUM);
            if (account.getSum() != sum){
                accountUtil.updateSum(account, sum, newAccount);
            }

            write(resp, SUCCESS_ANSWER);
        }
    }
}
