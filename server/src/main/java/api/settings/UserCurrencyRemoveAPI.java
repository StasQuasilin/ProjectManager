package api.settings;

import constants.API;
import controllers.ServletAPI;
import entity.accounts.UserCurrency;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(API.CURRENCY_REMOVE)
public class UserCurrencyRemoveAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            UserCurrency userCurrency = dao.getObjectById(UserCurrency.class, body.get(ID));
            dao.remove(userCurrency);
            write(resp, SUCCESS);
        }
    }
}
