package controllers.api.finance;

import constants.ApiLinks;
import controllers.api.API;
import entity.finance.Account;
import entity.finance.AccountType;
import entity.user.User;
import utils.db.dao.daoService;
import utils.db.dao.finance.accounts.AccountDAO;
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            User user = getUser(req);
            Account account = accountDAO.getAccount(body.get(ID));
            if(account == null){
                account = new Account();
                account.setOwner(user);
            }

            account.setTitle(body.getString(TITLE));
            AccountType type = AccountType.valueOf(body.getString(TYPE));
            account.setType(type);
            account.setSum(body.getFloat(SUM));
            account.setCurrency(body.getString(CURRENCY));
            int limit = body.getInt(LIMIT);
            if (type != AccountType.card && type != AccountType.credit){
                limit = 0;
            }
            account.setLimit(limit);

            accountDAO.saveAccount(account);

            write(resp, SUCCESS_ANSWER);
        }
    }
}
