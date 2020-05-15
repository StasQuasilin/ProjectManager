package api.transactions;

import constants.API;
import constants.Keys;
import controllers.ServletAPI;
import entity.accounts.Counterparty;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import services.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(API.FIND_COUNTERPARTY)
public class FindCounterpartyAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            User user = getUser(req);
            String key = String.valueOf(body.get(KEY));
            JSONArray array = pool.getArray();
            for (Counterparty counterparty : dao.findCounterparty(user, key)){
                array.add(counterparty.toJson());
            }
            JSONObject json = new SuccessAnswer(Keys.RESULT, array).toJson();
            write(resp, json.toJSONString());
            pool.put(json);

        }
    }
}
