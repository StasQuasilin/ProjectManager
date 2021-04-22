package controllers.api.finance;

import constants.ApiLinks;
import constants.Keys;
import controllers.api.API;
import entity.finance.Counterparty;
import entity.user.User;
import org.json.simple.JSONArray;
import utils.answers.Answer;
import utils.answers.SuccessAnswer;
import utils.finances.CounterpartyUtil;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(ApiLinks.FIND_COUNTERPARTY)
public class CounterpartyFindAPI extends API {

    private final CounterpartyUtil counterpartyUtil = new CounterpartyUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if(body != null){
            final String key = body.getString(Keys.KEY);
            final User user = getUser(req);
            JSONArray array = new JSONArray();
            for (Counterparty counterparty : counterpartyUtil.findCounterparty(key, user)){
                array.add(counterparty.shortJson());
            }
            Answer answer = new SuccessAnswer();
            answer.addAttribute(Keys.RESULT, array);
            write(resp, answer);
        }
    }
}
