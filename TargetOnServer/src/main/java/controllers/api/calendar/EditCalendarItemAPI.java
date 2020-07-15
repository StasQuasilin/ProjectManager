package controllers.api.calendar;

import constants.ApiLinks;
import controllers.api.API;
import entity.calendar.CalendarItem;
import entity.calendar.Repeat;
import entity.finance.Category;
import entity.user.User;
import utils.db.dao.calendar.CalendarDAO;
import utils.db.dao.daoService;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

import static constants.Keys.*;

@WebServlet(ApiLinks.CALENDAR_EDIT)
public class EditCalendarItemAPI extends API {

    private final CalendarDAO calendarDAO = daoService.getCalendarDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            CalendarItem item = calendarDAO.getCalendarItem(body.get(ID));
            if (item == null){
                item = new CalendarItem();
            }

            final User user = getUser(req);
            Category category = item.getCategory();
            if (category == null){
                category = new Category();
                category.setOwner(user);
                item.setCategory(category);
            }

            final String title = body.getString(TITLE);
            category.setTitle(title);

            boolean useDate = body.getBoolean(USE_DATE);

            if (useDate){
                Timestamp timestamp = body.getTimestamp(DATE);
                final LocalDate localDate = timestamp.toLocalDateTime().toLocalDate();
                item.setDate(Date.valueOf(localDate));
                boolean useTime = body.getBoolean(USE_TIME);
                if (useTime){
                    final LocalTime localTime = timestamp.toLocalDateTime().toLocalTime();
                    item.setTime(Time.valueOf(localTime));
                } else {
                    item.setTime(null);
                }
            }  else {
                item.setDate(null);
                item.setTime(null);
            }

            long length = body.getLong(LENGTH);
            item.setLength(length);

            Repeat repeat = Repeat.valueOf(body.getString(REPEAT));
            item.setRepeat(repeat);

            calendarDAO.saveCalendarItem(item);
        }
        write(resp, SUCCESS_ANSWER);
    }
}
