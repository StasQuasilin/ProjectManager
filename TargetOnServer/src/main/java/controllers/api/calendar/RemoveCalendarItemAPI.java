package controllers.api.calendar;

import constants.ApiLinks;
import controllers.api.API;
import entity.calendar.CalendarItem;
import utils.db.dao.calendar.CalendarDAO;
import utils.db.dao.daoService;
import utils.json.JsonObject;
import utils.removers.CalendarRemover;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.ID;

@WebServlet(ApiLinks.CALENDAR_REMOVE)
public class RemoveCalendarItemAPI extends API {

    private final CalendarDAO calendarDAO = daoService.getCalendarDAO();
    private final CalendarRemover calendarRemover = new CalendarRemover();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            final CalendarItem item = calendarDAO.getCalendarItem(body.get(ID));
            write(resp, SUCCESS_ANSWER);
            calendarRemover.remove(item);
        } else {
            write(resp, SUCCESS_ANSWER);
        }
    }
}
