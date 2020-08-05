package controllers;

import constants.ApiLinks;
import constants.UrlLinks;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import static constants.Keys.*;

@WebServlet(UrlLinks.HOME)
public class Application extends HttpServlet {
    private static final String PAGE = "/pages/application.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(GOALS, UrlLinks.GOALS);
        req.setAttribute(TREE, UrlLinks.TREE);
        req.setAttribute(CALENDAR, UrlLinks.CALENDAR);
        req.setAttribute(FINANCES, UrlLinks.FINANCES);
        req.setAttribute(PERSONAL, UrlLinks.PERSONAL);
        req.setAttribute(LOGOUT, ApiLinks.LOGOUT);
        req.setAttribute(SUBSCRIBE, ApiLinks.SUBSCRIBE);
        req.setAttribute(WELCOME, UrlLinks.GOALS);
        req.setAttribute(YEAR, LocalDate.now().getYear());
        req.getRequestDispatcher(PAGE).forward(req, resp);
    }
}
