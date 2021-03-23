package controllers.api.finance;

import constants.ApiLinks;
import constants.Keys;
import controllers.api.API;
import entity.finance.accounts.PointScale;
import entity.user.User;
import utils.answers.Answer;
import utils.answers.ErrorAnswer;
import utils.answers.SuccessAnswer;
import utils.db.dao.daoService;
import utils.finances.CategoryReportBuilder;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

import static constants.Keys.RESULT;

@WebServlet(ApiLinks.BUILD_MONTH_REPORT)
public class MonthReportApi extends API {

    private final CategoryReportBuilder reportBuilder = new CategoryReportBuilder();
    final PointScale scale = PointScale.month;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        System.out.println(body);
        Answer answer;
        if(body != null){
            answer = new SuccessAnswer();
            final Date date = body.getDate(Keys.DATE);
            final User user = getUser(req);
            answer.addAttribute(RESULT, reportBuilder.buildReport(scale, date, user));
        } else {
            answer = new ErrorAnswer("no body");
        }
        write(resp, answer);
    }
}
