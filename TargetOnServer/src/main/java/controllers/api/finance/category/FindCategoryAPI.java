package controllers.api.finance.category;

import constants.ApiLinks;
import controllers.api.API;
import entity.finance.category.Header;
import entity.user.User;
import org.json.simple.JSONArray;
import utils.answers.Answer;
import utils.answers.ErrorAnswer;
import utils.answers.SuccessAnswer;
import utils.db.dao.TitleDAO;
import utils.db.dao.daoService;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.KEY;
import static constants.Keys.RESULT;

@WebServlet(ApiLinks.FIND_CATEGORY)
public class FindCategoryAPI extends API {

    private final TitleDAO titleDAO = daoService.getTitleDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            final String key = body.getString(KEY);
            final User user = getUser(req);
            Answer answer;
            if (user != null){
                JSONArray array = new JSONArray();
                for (Header category : titleDAO.findHeader(key, user)){
                    array.add(category.shortJson());
                }
                answer = new SuccessAnswer();
                answer.addAttribute(RESULT, array);

            } else {
                answer = new ErrorAnswer("No user data");
            }
            write(resp, answer);
        }
    }
}
