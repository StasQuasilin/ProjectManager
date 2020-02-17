package api.login;

import constants.API;
import entity.RegistrationConfirm;
import entity.user.UserAccess;
import services.LanguageBase;
import services.answers.ErrorAnswer;
import services.answers.IAnswer;
import services.answers.SuccessAnswer;
import services.hibernate.Hibernator;
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
 * Created by quasilin on 22.02.2019.
 */
@WebServlet(API.REGISTRATION)
public class RegistrationAPI extends HttpServlet{

    private static final Hibernator hibernator = Hibernator.getInstance();
    private static final LanguageBase language = LanguageBase.getBase();
    public static final String ALREADY_USE = "registration.error.login.already.use";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> body = PostUtil.parseBody(req);

        String email = body.get("email");
        String password = body.get("password");
        String lang = "ru";

        IAnswer answer;
        UserAccess userAccess = hibernator.get(UserAccess.class, "login", email);
        if (userAccess != null){
            answer = new ErrorAnswer();
            answer.add("msg", language.get(lang, ALREADY_USE));
        } else {
            RegistrationConfirm confirm = hibernator.get(RegistrationConfirm.class, "email", email);
            if (confirm == null){
                confirm = new RegistrationConfirm();
            }
            confirm.setPassword(password);
            hibernator.save(confirm);
            answer = new SuccessAnswer();
        }


//        PostUtil.write(resp, JsonParser.toJson(answer).toJSONString());
        body.clear();

    }
}
