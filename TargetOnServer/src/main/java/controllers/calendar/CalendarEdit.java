package controllers.calendar;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.ModalWindow;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(UrlLinks.CALENDAR_EDIT)
public class CalendarEdit extends ModalWindow {
    private static final String _TITLE = "title.calendar.edit";
    private static final String _CONTENT = "/pages/calendar/calendarEdit.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(SAVE, ApiLinks.CALENDAR_EDIT);
        show(req, resp);
    }
}
