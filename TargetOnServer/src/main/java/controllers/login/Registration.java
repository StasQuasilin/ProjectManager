package controllers.login;

import constants.ApiLinks;
import constants.UrlLinks;
import utils.LanguageBase;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;
import static constants.Keys.REGISTRATION;

@WebServlet(UrlLinks.REGISTRATION)
public class Registration extends HttpServlet {

    private static final String REGISTRATION_PAGE = "/pages/login/registration.jsp";
    private final LanguageBase languageBase = LanguageBase.getBase();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String lang = req.getParameter(LANG);
        req.setAttribute(CONTEXT, req.getContextPath());
        req.setAttribute(LOCALE, languageBase.getLocale(lang));
        req.setAttribute(LOCALES, languageBase.getLocales());
        req.setAttribute(REGISTRATION, ApiLinks.REGISTRATION);
        req.setAttribute(LOGIN, UrlLinks.LOGIN);
        req.getRequestDispatcher(REGISTRATION_PAGE).forward(req, resp);
    }
}
