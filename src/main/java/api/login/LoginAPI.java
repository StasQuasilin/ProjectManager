package api.login;

import constants.API;
import constants.Branches;
import constants.Keys;
import controllers.ServletAPI;
import entity.user.UserAccess;
import filters.LoginFilter;
import org.json.simple.JSONObject;
import services.LanguageBase;
import services.answers.ErrorAnswer;
import services.answers.IAnswer;
import services.answers.SuccessAnswer;
import services.hibernate.Hibernator;
import services.hibernate.dbDAO;
import services.hibernate.dbDAOService;
import utils.JsonParser;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 22.02.2019.
 */
@WebServlet(API.LOGIN)
public class LoginAPI extends ServletAPI implements Keys {

    private final dbDAO dao = dbDAOService.getDao();
    private static final LanguageBase lang = LanguageBase.getBase();
    public static final String NO_USER = "login.error.no.user";
    public static final String WRONG_PASSWORD = "login.error.wrong.password";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            String language = "ru";

            IAnswer answer;
            UserAccess userAccess = dao.getUserAccessByEmail(body.get(EMAIL));

            if (userAccess == null){
                answer = new ErrorAnswer();
                answer.add("msg", lang.get(language, NO_USER));
            } else if (!userAccess.getPassword().equals(body.get(PASSWORD))){
                answer = new ErrorAnswer();
                answer.add("msg", lang.get(language, WRONG_PASSWORD));
            } else {
                req.getSession().setAttribute(UID, userAccess.getId());
                req.getSession().setAttribute(TOKEN, LoginFilter.addUser(userAccess));
                req.getSession().setAttribute(LANGUAGE, userAccess.getUser().getLanguage());
                answer = new SuccessAnswer();
                answer.add(REDIRECT, req.getContextPath() + Branches.HOME);
            }

            JSONObject json = answer.toJson();
            write(resp, json.toJSONString());
            pool.put(json);
        }

    }
}
