package api.login;

import constants.API;
import constants.Keys;
import controllers.ServletAPI;
import entity.RegistrationConfirm;
import entity.user.Person;
import entity.user.User;
import entity.user.UserAccess;
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
 * Created by quasilin on 22.02.2019.
 */
@WebServlet(API.REGISTRATION)
public class RegistrationAPI extends ServletAPI{

    private static final LanguageBase language = LanguageBase.getBase();
    public static final String ALREADY_USE = "registration.error.login.already.use";
    private final dbDAO dao = dbDAOService.getDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            String email = String.valueOf(body.get(EMAIL));
            String password = String.valueOf(body.get(PASSWORD));
            String lang = String.valueOf(body.get(LANGUAGE));
            String surname = String.valueOf(body.get(SURNAME));
            String forename = String.valueOf(body.get(FORENAME));

            IAnswer answer;

            UserAccess userAccess = dao.getUserAccessByEmail(email);

            if (userAccess != null){
                answer = new ErrorAnswer();
                answer.add("msg", language.get(lang, ALREADY_USE));
            } else {
                Person person = new Person();
                person.setSurname(surname);
                person.setForename(forename);
                dao.save(person);

                User user = new User();
                user.setLanguage(lang);
                user.setPerson(person);
                dao.save(user);

                userAccess = new UserAccess();
                userAccess.setEmail(email);
                userAccess.setPassword(password);
                userAccess.setUser(user);
                dao.save(userAccess);

                answer = new SuccessAnswer();
            }
            JSONObject json = answer.toJson();
            write(resp, json.toJSONString());
            pool.put(json);
            body.clear();
        }
    }
}
