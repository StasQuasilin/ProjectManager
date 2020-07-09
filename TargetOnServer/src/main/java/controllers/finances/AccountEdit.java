package controllers.finances;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.ModalWindow;
import entity.finance.Account;
import entity.finance.AccountType;
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

    private final AccountDAO accountDAO = daoService.getAccountDAO();
    private final CurrencyDAO currencyDAO = daoService.getCurrencyDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            Account account = accountDAO.getAccount(body.get(ID));
            if (account != null){
                System.out.println(account.getTitle());
            } else {
                System.out.println("Account is null");
            }
            req.setAttribute(ACCOUNT, account);
        }
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(TYPES, AccountType.values());
        User user = getUser(req);
        req.setAttribute(CURRENCY, currencyDAO.getUserCurrency(user));
        req.setAttribute(SAVE, ApiLinks.ACCOUNT_EDIT);
        show(req, resp);
    }
}
