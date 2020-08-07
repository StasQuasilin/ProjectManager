package controllers.api.login;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.api.API;
import entity.user.User;
import entity.user.UserAccess;
import entity.user.UserSettings;
import utils.LanguageBase;
import utils.answers.Answer;
import utils.answers.ErrorAnswer;
import utils.answers.SuccessAnswer;
import utils.db.dao.access.UserAccessDAO;
import utils.db.dao.daoService;
import utils.db.dao.user.UserDAO;
import utils.db.dao.user.UserDAOImpl;
import utils.json.JsonAble;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(ApiLinks.LOGIN)
public class LoginAPI extends API {

    private final UserAccessDAO userAccessDAO = daoService.getUserAccessDAO();
    private final UserDAO userDAO = daoService.getUserDAO();
    private final LanguageBase languageBase = LanguageBase.getBase();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if(body != null){
            final Object email = body.get(EMAIL);
            final UserAccess userAccess = userAccessDAO.getUserAccess(email);
            final String language = body.getString(LANGUAGE);
            Answer answer;
            if (userAccess != null){
                final String password = userAccess.getPassword();
                if (password.equals(body.getString(PASSWORD))){
                    final HttpSession session = req.getSession();
                    final User user = userAccess.getUser();
                    session.setAttribute(USER, user);
                    UserSettings settings = userDAO.getUserSettings(user);
                    String locale;
                    if (settings != null){
                        locale = settings.getLocale();
                        final String avatar = settings.getAvatar();
                        if (avatar != null){
                            session.setAttribute(AVATAR, avatar);
                        } else {
                            session.setAttribute(AVATAR, "/images/avatar.jpg");
                        }
                    } else {
                        locale = languageBase.getDefaultLocale();
                        session.setAttribute(AVATAR, "/images/avatar.jpg");
                    }
                    session.setAttribute(LOCALE, locale);

                    answer = new SuccessAnswer();
                    answer.addAttribute(REDIRECT, UrlLinks.HOME);
                } else {
                    answer = new ErrorAnswer();
                    answer.addAttribute(MESSAGE, languageBase.get(language, "wrong.password"));
                }
            } else {
                answer = new ErrorAnswer();
                answer.addAttribute(MESSAGE, languageBase.get(language, "user.not.found"));
            }

            write(resp, answer);
        }
    }


}
