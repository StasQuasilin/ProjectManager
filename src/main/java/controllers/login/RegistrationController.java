package controllers.login;

import constants.Branches;

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
public class RegistrationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "registration.title");
        req.setAttribute("currentPage", "/pages/login/registration.jsp");
        req.getRequestDispatcher("/base.jsp").forward(req, resp);
    }
}
