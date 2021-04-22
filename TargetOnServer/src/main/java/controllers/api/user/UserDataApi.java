package controllers.api.user;

import constants.ApiLinks;
import controllers.api.API;
import entity.finance.accounts.Account;
import entity.user.User;
import org.json.simple.JSONArray;
import utils.answers.Answer;
import utils.answers.SuccessAnswer;
import utils.db.dao.daoService;
import utils.db.dao.finance.accounts.AccountDAO;
import utils.db.dao.finance.currency.CurrencyDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.ACCOUNTS;
import static constants.Keys.CURRENCY;

@WebServlet(ApiLinks.USER_DATA)
public class UserDataApi extends API {

    final AccountDAO accountDAO = daoService.getAccountDAO();
    final CurrencyDAO currencyDAO = daoService.getCurrencyDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final User user = getUser(req);
        Answer answer = new SuccessAnswer();
        JSONArray accounts = new JSONArray();
        for (Account account : accountDAO.getUserAccounts(user, false)){
            accounts.add(account.toJson());
        }
        answer.addAttribute(ACCOUNTS, accounts);
        JSONArray currencies = new JSONArray();
        for (String currency : currencyDAO.getUserCurrency(user)){
            currencies.add(currency);
        }
        answer.addAttribute(CURRENCY, currencies);
        write(resp, answer);
    }
}
