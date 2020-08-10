package controllers.finances;

import constants.UrlLinks;
import controllers.ModalWindow;
import entity.finance.accounts.AccountPoint;
import entity.finance.transactions.TransactionPoint;
import utils.db.dao.daoService;
import utils.db.dao.finance.accounts.AccountDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static constants.Keys.*;

@WebServlet(UrlLinks.ACCOUNT_EXTRACT)
public class AccountExtract extends ModalWindow {

    private static final String _TITLE = "title.account.extract";
    private static final String _CONTENT = "/pages/finances/accounts/accountsExtract.jsp";
    private final AccountDAO accountDAO = daoService.getAccountDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);

        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        show(req, resp);
    }
}
