package controllers.calendar;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.ModalWindow;
import entity.calendar.CalendarItem;
import utils.db.dao.calendar.CalendarDAO;
import utils.db.dao.daoService;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(UrlLinks.CALENDAR_REMOVE)
public class CalendarRemove extends ModalWindow {

    private static final String _TITLE = "title.calendar.remove";
    private static final String _CONTENT = "/pages/calendar/calendarRemove.jsp";
    private final CalendarDAO calendarDAO = daoService.getCalendarDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            if (body.containKey(ID)) {
                final CalendarItem item = calendarDAO.getCalendarItem(body.get(ID));
                req.setAttribute(ITEM, item);
                req.setAttribute(TITLE, _TITLE);
                req.setAttribute(CONTENT, _CONTENT);
                req.setAttribute(REMOVE, ApiLinks.CALENDAR_REMOVE);
                show(req, resp);
            }
        }
    }
}
