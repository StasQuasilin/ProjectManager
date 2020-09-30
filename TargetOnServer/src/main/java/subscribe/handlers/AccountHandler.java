package subscribe.handlers;

import entity.finance.accounts.Account;
import entity.finance.accounts.AccountType;
import entity.finance.accounts.DepositSettings;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import subscribe.Subscribe;
import utils.db.dao.daoService;
import utils.db.dao.finance.accounts.AccountDAO;

import static constants.Keys.SETTINGS;

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
            final JSONObject jsonObject = account.toJson();
            if (account.getType() == AccountType.deposit){
                final DepositSettings depositSettings = accountDAO.getDepositSettings(account);
                if (depositSettings != null) {
                    jsonObject.put(SETTINGS, depositSettings.toJson());
                }
            }
            array.add(jsonObject);
        }
        System.out.println(array.toJSONString());
        return array;
    }
}
