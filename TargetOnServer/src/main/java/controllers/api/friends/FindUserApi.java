package controllers.api.friends;

import constants.ApiLinks;
import constants.Keys;
import controllers.api.API;
import entity.user.User;
import org.json.simple.JSONArray;
import utils.answers.Answer;
import utils.answers.ErrorAnswer;
import utils.answers.SuccessAnswer;
import utils.db.dao.daoService;
import utils.db.dao.user.UserDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(ApiLinks.FIND_USER)
public class FindUserApi extends API {

    private final UserDAO userDAO = daoService.getUserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        Answer answer;
        if (body != null){
            final String key = body.getString(Keys.KEY);
            final JSONArray array = new JSONArray();
            final User user = getUser(req);
            for (User u : userDAO.findUser(key, user)){
                array.add(u.toJson());
            }
            answer = new SuccessAnswer();
            answer.addAttribute(Keys.RESULT, array);
        } else {
            answer = new ErrorAnswer("request body is empty");
        }
        write(resp, answer);
    }
}
