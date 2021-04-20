package controllers.calendar;

import constants.ApiLinks;
import constants.Keys;
import constants.UrlLinks;
import controllers.Page;
import subscribe.Subscribe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

/**
 * Created by DELL on 06.07.2020.
 */
@WebServlet(UrlLinks.CALENDAR)
public class CalendarPage extends Page {

    private static final String _CONTENT = "/pages/calendar/calendarPage.jsp";
    private static final String _TITLE = "title.calendar";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(GET_CALENDAR, ApiLinks.GET_CALENDAR);
        req.setAttribute(EDIT, UrlLinks.CALENDAR_EDIT);
        req.setAttribute(REMOVE, UrlLinks.CALENDAR_REMOVE);
        req.setAttribute(Keys.RANDOM_TASK, UrlLinks.RANDOM_TASK);
        req.setAttribute(TASK_SUBSCRIBE, Subscribe.calendar_tasks);

        req.setAttribute(SUBSCRIBE, Subscribe.calendar);
        show(req, resp);
    }
}
