package controllers.api.finance;

import constants.ApiLinks;
import constants.Keys;
import controllers.api.API;
import org.json.simple.parser.ParseException;
import utils.answers.Answer;
import utils.answers.ErrorAnswer;
import utils.answers.SuccessAnswer;
import utils.db.hibernate.Hibernator;
import utils.finances.FinanceUtil;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.RESULT;

@WebServlet(ApiLinks.EXCHANGE_RATE)
public class ExchangeRateApi extends API {

    final Hibernator hibernator = Hibernator.getInstance();
    FinanceUtil financeUtil = new FinanceUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if(body != null){
            final String base = body.getString(Keys.BASE);
            final String currency = body.getString(Keys.CURRENCY);
            Answer answer;
            try {
                final JsonObject exchangeRate = financeUtil.getExchangeRate(base, currency);
                answer = new SuccessAnswer();
                answer.addAttribute(RESULT, exchangeRate);
            } catch (ParseException e) {
                e.printStackTrace();
                answer = new ErrorAnswer(e.getMessage());
            }
            write(resp, answer);
        }
    }


    //            final ExternalService service = hibernator.get(ExternalService.class, Keys.TYPE, ServiceType.exchange);
}
