package controllers.login;

import constants.API;
import constants.Branches;
import controllers.IPage;
import services.LanguageBase;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 22.02.2019.
 */
@WebServlet(Branches.REGISTRATION)
public class RegistrationController extends IPage {

    private static final String _TITLE = "registration.title";
    private static final String _CONTENT = "/pages/login/registration.jsp";
    LanguageBase languageBase = LanguageBase.getBase();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(LANGUAGES, languageBase.getLanguages());
        req.setAttribute(REGISTRATION, API.REGISTRATION);
        req.getRequestDispatcher(_CONTENT).forward(req, resp);

    }
}
