package controllers;

import constants.Branches;
import constants.Keys;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 14.02.2020.
 */
@WebServlet(Branches.HOME)
public class Application extends HttpServlet implements Keys {

    public static final String APPLICATION = "/pages/application.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(PROJECTS, Branches.PROJECTS);
        req.setAttribute(BUDGET, Branches.BUDGET);
        req.getRequestDispatcher(APPLICATION).forward(req, resp);
    }
}
