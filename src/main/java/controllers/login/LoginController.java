package controllers.login;

import constants.Branches;
import controllers.IPage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 22.02.2019.
 */
@WebServlet(Branches.LOGIN)
public class LoginController extends IPage {

    private static final String _TITLE = "login.title";
    private static final String _CONTENT = "/pages/login/login.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.getRequestDispatcher(_CONTENT).forward(req, resp);


    }
}
