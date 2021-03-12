package controllers.api.finance;

import constants.ApiLinks;
import controllers.api.API;
import entity.user.User;
import org.json.simple.JSONArray;
import utils.answers.Answer;
import utils.answers.ErrorAnswer;
import utils.answers.SuccessAnswer;
import utils.db.dao.daoService;
import utils.db.dao.finance.currency.CurrencyDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(ApiLinks.FIND_CURRENCY)
public class FindCurrencyApi extends API {

    private final CurrencyDAO currencyDAO = daoService.getCurrencyDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        Answer answer;
        if (body != null){
            JSONArray array = new JSONArray();
            final User user = getUser(req);
            for (String currency : currencyDAO.findCurrency(body.getString(KEY), user)){
                array.add(currency);
            }
            answer = new SuccessAnswer();
            answer.addAttribute(RESULT, array);
        } else {
            answer = new ErrorAnswer(EMPTY_BODY);
        }
        write(resp, answer);
    }
}
