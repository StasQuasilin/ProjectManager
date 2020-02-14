package controllers.settings;

import constants.Branches;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by quasilin on 24.02.2019.
 */
@WebServlet(Branches.SETTINGS)
public class SettingsList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "settings.title");
        req.setAttribute("currentPage", "/pages/home/home.jsp");
        req.setAttribute("pageContent", "/pages/settings/settingsList.jsp");
        req.getRequestDispatcher("/base.jsp").forward(req, resp);

    }
}
