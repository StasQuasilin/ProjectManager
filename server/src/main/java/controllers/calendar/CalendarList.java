package controllers.calendar;

import constants.Links;
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
@WebServlet(Links.CALENDAR)
public class CalendarList extends IPage {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "calendar.title");
        req.setAttribute("pageContent", "/pages/calendar/calendarList.jsp");
        showPage(req, resp);

    }
}
