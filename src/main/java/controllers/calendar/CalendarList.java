package controllers.calendar;

import api.socket.Subscribe;
import constants.API;
import constants.Branches;
import controllers.IPage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by quasilin on 24.02.2019.
 */
@WebServlet(Branches.CALENDAR)
public class CalendarList extends IPage {
    private static final String _TITLE = "calendar.title";
    private static final String _CONTENT = "/pages/calendar/calendarList.jsp";
    private static final Subscribe[] subscribe = new Subscribe[]{Subscribe.calendar};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(PAGE_CONTENT, _CONTENT);
        req.setAttribute(EDIT, Branches.TASK_EDIT);
        req.setAttribute(EDIT_TIME, API.TASK_TIME_EDIT);
        req.setAttribute(GET_ITEMS, API.GET_CALENDAR_ITEMS);
        req.setAttribute(SUBSCRIBES, subscribe);
        showPage(req, resp);

    }
}
