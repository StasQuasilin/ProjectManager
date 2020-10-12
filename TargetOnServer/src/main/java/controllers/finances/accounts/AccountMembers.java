package controllers.finances.accounts;

import constants.ApiLinks;
import constants.Keys;
import constants.UrlLinks;
import controllers.ModalWindow;
import controllers.goals.GoalMembers;
import entity.user.User;
import utils.db.dao.daoService;
import utils.db.dao.finance.accounts.AccountDAO;
import utils.db.dao.user.FriendshipDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(UrlLinks.ACCOUNT_MEMBERS)
public class AccountMembers extends ModalWindow {

    private static final String _TITLE = "title.account.members";
    private final FriendshipDAO friendshipDAO = daoService.getFriendshipDAO();
    private final AccountDAO accountDAO = daoService.getAccountDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            final User user = getUser(req);
            req.setAttribute(Keys.FRIENDS, friendshipDAO.getFriends(user));
            final Object account = body.get(Keys.ACCOUNT);
            req.setAttribute(Keys.ITEM, account);
            req.setAttribute(Keys.OWNER, user);
            req.setAttribute(Keys.MEMBERS, accountDAO.getMembers(account));
            req.setAttribute(Keys.TITLE, _TITLE);
            req.setAttribute(Keys.CONTENT, GoalMembers._CONTENT);
            req.setAttribute(Keys.SAVE, ApiLinks.SAVE_ACCOUNT_MEMBERS);
            show(req, resp);
        }
    }
}
