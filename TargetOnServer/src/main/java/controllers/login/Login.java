package controllers.login;

import constants.ApiLinks;
import constants.Keys;
import constants.UrlLinks;
import utils.LanguageBase;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(UrlLinks.LOGIN)
public class Login extends HttpServlet {

    private static final String LOGIN_PAGE = "/pages/login/login.jsp";
    private final LanguageBase languageBase = LanguageBase.getBase();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String lang = req.getParameter(LANG);
        req.setAttribute(CONTEXT, req.getContextPath());
        req.setAttribute(LOCALE, languageBase.getLocale(lang));
        req.setAttribute(LOCALES, languageBase.getLocales());
        req.setAttribute(LOGIN, ApiLinks.LOGIN);
        req.setAttribute(REGISTRATION, UrlLinks.REGISTRATION);
        req.setAttribute(Keys.DEMO, ApiLinks.DEMO);
        req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
    }
}
