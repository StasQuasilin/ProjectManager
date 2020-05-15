package controllers.budget;

import constants.API;
import constants.Branches;
import controllers.IModal;
import entity.accounts.Account;
import entity.accounts.BudgetSize;
import entity.accounts.AccountType;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by szpt_user045 on 27.02.2020.
 */
@WebServlet(Branches.ACCOUNT_EDIT)
public class AccountEdit extends IModal {
    private static final String _TITLE = "title.budget.edit";
    private static final String _CONTENT = "/pages/account/budgetEdit.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        Account account = null;
        if (body != null){
            System.out.println(body);
            if (body.containsKey(ID)) {
                account = dao.getObjectById(Account.class, body.get(ID));
                req.setAttribute(ACCOUNT, account);
                if (account != null && account.getAccountType() == AccountType.deposit) {
                    req.setAttribute(SETTINGS, dao.getDepositSettingsByAccount(account));
                }
            }
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(PAGE_CONTENT, _CONTENT);
        req.setAttribute(TYPES, AccountType.values());
        req.setAttribute(CURRENCY, dao.getUserCurrency(getUser(req)));
        req.setAttribute(SIZES, BudgetSize.values());
        req.setAttribute(SAVE, API.BUDGET_EDIT);
        List<Account> accounts = dao.getAccountsByUser(getUser(req));
        if (account != null){
            accounts.remove(account);
        }
        req.setAttribute(ACCOUNTS, accounts);
        show(req, resp);
    }
}
