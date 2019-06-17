package controllers.home;

import constants.Links;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by quasilin on 23.02.2019.
 */
@WebServlet(Links.HOME)
public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("uid", 1);
        req.setAttribute("projects", Links.PROJECTS);
        req.setAttribute("tree", Links.TREE);
        req.setAttribute("calendar", Links.CALENDAR);
        req.setAttribute("budget", Links.BUDGET);
        req.setAttribute("payments", Links.PAYMENTS);
        req.setAttribute("settings", Links.SETTINGS);
        req.getRequestDispatcher("/pages/home/home.jsp").forward(req, resp);
    }
}
