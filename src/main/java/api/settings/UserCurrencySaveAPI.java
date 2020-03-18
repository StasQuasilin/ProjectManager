package api.settings;

import constants.API;
import controllers.ServletAPI;
import entity.budget.UserCurrency;
import entity.user.User;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(API.CURRENCY_EDIT)
public class UserCurrencySaveAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            UserCurrency userCurrency = dao.getObjectById(UserCurrency.class, body.get(ID));
            User user = getUser(req);
            if (userCurrency == null){
                userCurrency = new UserCurrency();
                userCurrency.setUser(user);
            }
            userCurrency.setMain(Boolean.parseBoolean(String.valueOf(body.get(MAIN))));

            dao.save(userCurrency);
            write(resp, SUCCESS);

        }
    }
}
