package controllers.finances.accounts;

import constants.ApiLinks;
import constants.Keys;
import constants.UrlLinks;
import controllers.ModalWindow;
import entity.finance.accounts.Account;
import entity.finance.accounts.AccountType;
import entity.finance.accounts.CardSettings;
import entity.finance.accounts.DepositSettings;
import entity.user.User;
import utils.db.dao.daoService;
import utils.db.dao.finance.accounts.AccountDAO;
import utils.db.dao.finance.currency.CurrencyDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

/**
 * Created by DELL on 07.07.2020.
 */
@WebServlet(UrlLinks.ACCOUNT_EDIT)
public class AccountEdit extends ModalWindow {

    private static final String _CONTENT = "/pages/finances/accountEdit.jsp";
    private static final String _TITLE = "title.account.edit";

    private final AccountDAO accountDAO = daoService.getAccountDAO();
    private final CurrencyDAO currencyDAO = daoService.getCurrencyDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject body = parseBody(req);
        if (body != null){
            Account account = accountDAO.getAccount(body.get(ID));
            req.setAttribute(ACCOUNT, account);

            final AccountType type = account.getType();
            if (type == AccountType.deposit) {
                final DepositSettings depositSettings = accountDAO.getDepositSettings(account);
                if (depositSettings != null) {
                    req.setAttribute(SETTINGS, depositSettings);
                }
            } else if (type == AccountType.card){
                final CardSettings cardSettings = accountDAO.getCardSettings(account);
                if (cardSettings != null){
                    req.setAttribute(SETTINGS, cardSettings);
                }
            }
            req.setAttribute(MEMBERS, accountDAO.getMembers(account));
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(TYPES, AccountType.values());
        User user = getUser(req);
        req.setAttribute(CURRENCY, currencyDAO.getUserCurrency(user));
        req.setAttribute(SAVE, ApiLinks.ACCOUNT_EDIT);
        req.setAttribute(Keys.MEMBER_LIST, UrlLinks.ACCOUNT_MEMBERS);
        show(req, resp);
    }
}
