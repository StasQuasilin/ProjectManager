package api.transactions;

import constants.API;
import constants.Keys;
import controllers.ServletAPI;
import entity.transactions.TransactionCategory;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import services.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 26.02.2020.
 */
@WebServlet(API.FIND_TRANSACTION_CATEGORY)
public class FindTransactionCategoryAPI extends ServletAPI implements Keys {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            String key = String.valueOf(body.get(KEY));
            User user = getUser(req);

            JSONArray array = pool.getArray();
            for (TransactionCategory category : dao.findTransactionCategory(key, user)){
                array.add(category.toJson());
            }

            JSONObject json = new SuccessAnswer(RESULT, array).toJson();
            write(resp, json.toJSONString());
            pool.put(json);
        }
    }
}
