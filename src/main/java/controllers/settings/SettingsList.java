package controllers.settings;

import constants.Branches;
import controllers.IPage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by quasilin on 24.02.2019.
 */
@WebServlet(Branches.SETTINGS)
public class SettingsList extends IPage {

    private static final String _TITLE = "settings.title";
    private static final String _CONTENT = "/pages/settings/settingsPage.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(PAGE_CONTENT, _CONTENT);
        req.setAttribute(CURRENCY_SETTINGS, Branches.CURRENCY_SETTINGS);
        showPage(req, resp);

    }
}
