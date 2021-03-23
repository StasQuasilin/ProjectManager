package controllers.finances;

import constants.ApiLinks;
import constants.Keys;
import constants.UrlLinks;
import controllers.ModalWindow;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.CONTENT;
import static constants.Keys.TITLE;

@WebServlet(UrlLinks.REPORT_OF_MONTH)
public class ReportOfMonth extends ModalWindow {
    private static final String _TITLE = "title.report.of.month";
    private static final String _CONTENT = "/pages/finances/monthReport.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(Keys.REPORT_BUILD_API, ApiLinks.BUILD_MONTH_REPORT);
        show(req, resp);
    }
}
