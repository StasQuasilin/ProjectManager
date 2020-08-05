package controllers.api.login;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.api.API;
import entity.user.User;
import entity.user.UserAccess;
import utils.LanguageBase;
import utils.answers.Answer;
import utils.answers.ErrorAnswer;
import utils.answers.SuccessAnswer;
import utils.db.dao.access.UserAccessDAO;
import utils.db.dao.daoService;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(ApiLinks.REGISTRATION)
public class RegistrationAPI extends API {

    private final LanguageBase languageBase = LanguageBase.getBase();
    private final UserAccessDAO userAccessDAO = daoService.getUserAccessDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            final String locale = body.getString(LOCALE);
            Answer answer;
            final Object email = body.get(EMAIL);
            UserAccess userAccess = userAccessDAO.getUserAccess(email);
            if (userAccess == null){
                userAccess = new UserAccess();
                userAccess.setLogin(String.valueOf(email));
                userAccess.setPassword(body.getString(PASSWORD));
                User user = new User();
                user.setSurname(body.getString(SURNAME));
                user.setForename(body.getString(FORENAME));
                userAccess.setUser(user);

                userAccessDAO.userRegistration(userAccess);
                answer = new SuccessAnswer();
                answer.addAttribute(REDIRECT, UrlLinks.LOGIN);

            } else {
                answer = new ErrorAnswer();
                answer.addAttribute(MESSAGE, languageBase.get(locale, "email.already.use"));
            }
            write(resp, answer);
        }
    }
}
