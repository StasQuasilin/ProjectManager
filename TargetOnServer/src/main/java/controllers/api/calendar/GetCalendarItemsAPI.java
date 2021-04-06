package controllers.api.calendar;

import constants.ApiLinks;
import controllers.api.API;
import entity.calendar.CalendarItem;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.db.dao.calendar.CalendarDAO;
import utils.db.dao.daoService;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

import static constants.Keys.DATE;

@WebServlet(ApiLinks.GET_CALENDAR)
public class GetCalendarItemsAPI extends API {

    private final CalendarDAO calendarDAO = daoService.getCalendarDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            final User user = getUser(req);
            Date date = body.getDate(DATE);
            JSONArray array = new JSONArray();
            for (CalendarItem item : calendarDAO.getCalendarItems(date, user)){
                array.add(item.toJson());
            }
            write(resp, array.toJSONString());

        }
    }
}
