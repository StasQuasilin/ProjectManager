package subscribe.handlers;

import entity.finance.accounts.Account;
import entity.user.User;
import org.json.simple.JSONArray;
import subscribe.Subscribe;
import utils.db.dao.daoService;
import utils.db.dao.finance.accounts.AccountDAO;

/**
 * Created by DELL on 08.07.2020.
 */
public class AccountHandler extends SubscribeHandler {

    private final AccountDAO accountDAO = daoService.getAccountDAO();

    public AccountHandler() {
        super(Subscribe.accounts);
    }

    @Override
    Object getItems(User user) {
        JSONArray array = new JSONArray();
        for (Account account : accountDAO.getUserAccounts(user)){
            array.add(account.toJson());
        }
        System.out.println(array.toJSONString());
        return array;
    }
}
