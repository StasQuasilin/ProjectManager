package controllers;

import constants.ApiLinks;
import constants.Keys;
import constants.UrlLinks;
import subscribe.Subscribe;
import utils.db.dao.daoService;
import utils.db.dao.user.UserDAO;

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
        req.setAttribute(KANBAN, UrlLinks.KANBAN);
        req.setAttribute(CALENDAR, UrlLinks.CALENDAR);
        req.setAttribute(FINANCES, UrlLinks.FINANCES);
        req.setAttribute(FRIENDS, UrlLinks.FRIENDS);
        req.setAttribute(MESSAGES, UrlLinks.MESSAGES);
        req.setAttribute(PERSONAL, UrlLinks.PERSONAL);
        req.setAttribute(WELCOME, UrlLinks.GOALS);
        req.setAttribute(LOGOUT, ApiLinks.LOGOUT);
        req.setAttribute(Keys.ACCOUNT_REMOVE, ApiLinks.ACCOUNT_REMOVE);
        req.setAttribute(SUBSCRIBE, ApiLinks.SUBSCRIBE);
        req.setAttribute(TIMER_SUBSCRIBE, Subscribe.timer);
        req.setAttribute(TIMER_STOP, ApiLinks.TIMER_STOP);
        req.setAttribute(YEAR, LocalDate.now().getYear());
        req.getRequestDispatcher(PAGE).forward(req, resp);
    }
}
