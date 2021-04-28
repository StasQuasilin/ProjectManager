package controllers.api.login;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.api.API;
import entity.user.User;
import entity.user.UserAccess;
import entity.user.UserSettings;
import org.json.simple.JSONObject;
import utils.LanguageBase;
import utils.Validator;
import utils.answers.Answer;
import utils.answers.ErrorAnswer;
import utils.answers.SuccessAnswer;
import utils.db.dao.access.UserAccessDAO;
import utils.db.dao.daoService;
import utils.db.dao.user.UserDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static constants.Keys.*;

@WebServlet(ApiLinks.REGISTRATION)
public class RegistrationAPI extends API {

    private final LanguageBase languageBase = LanguageBase.getBase();
    private final UserAccessDAO userAccessDAO = daoService.getUserAccessDAO();
    private final UserDAO userDAO = daoService.getUserDAO();
    private final Validator validator = new Validator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            final String locale = body.getString(LOCALE);
            Answer answer;
            JSONObject errors = new JSONObject();

            final Object email = body.get(EMAIL);
            if (!validEmail(String.valueOf(email))){
                answer = new ErrorAnswer(languageBase.getOrDefault(locale, "email.invalid"));
                errors.put(EMAIL, true);
            } else {
                UserAccess userAccess = userAccessDAO.getUserAccess(email);
                if (userAccess == null){
                    userAccess = new UserAccess();

                    userAccess.setLogin(String.valueOf(email));
                    final String password = body.getString(PASSWORD);
                    if (!validator.passwordValid(password)){
                        answer = new ErrorAnswer(languageBase.getOrDefault(locale, "wrong.password"));
                    } else {
                        userAccess.setPasswordHash(password.hashCode());
                        User user = new User();
                        final String surname = body.getString(SURNAME);
                        final String forename = body.getString(FORENAME);
                        if (validName(surname + forename)){
                            user.setSurname(surname);
                            user.setForename(forename);
                            userAccess.setUser(user);
                            userAccess.setToken(UUID.randomUUID().toString());
                            userAccessDAO.userRegistration(userAccess);

                            UserSettings settings = new UserSettings();
                            settings.setLocale(locale);
                            settings.setUser(user);
                            userDAO.saveSettings(settings);

                            answer = new SuccessAnswer();
                            answer.addAttribute(REDIRECT, UrlLinks.LOGIN);
                        } else {
                            answer = new ErrorAnswer(languageBase.getOrDefault(locale, "wrong.name"));
                            errors.put(SURNAME, true);
                            errors.put(FORENAME, true);
                        }
                    }

                } else {
                    answer = new ErrorAnswer(languageBase.getOrDefault(locale, "email.already.use"));
                    errors.put(EMAIL, true);
                }
            }
            answer.addAttribute(ERRORS, errors);
            write(resp, answer);
        }
    }

    private boolean validName(String name) {
        return validator.nameValidator(name);
    }

    private boolean validEmail(String email) {
        return validator.emailValid(email);
    }
}
