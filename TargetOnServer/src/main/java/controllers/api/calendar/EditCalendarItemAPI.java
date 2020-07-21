package controllers.api.calendar;

import constants.ApiLinks;
import controllers.api.API;
import entity.calendar.CalendarItem;
import entity.calendar.Repeat;
import entity.finance.category.Category;
import entity.user.User;
import utils.CategoryUtil;
import utils.db.dao.calendar.CalendarDAO;
import utils.db.dao.daoService;
import utils.json.JsonObject;
import utils.savers.CalendarSaver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static constants.Keys.*;

@WebServlet(ApiLinks.CALENDAR_EDIT)
public class EditCalendarItemAPI extends API {

    private final CalendarDAO calendarDAO = daoService.getCalendarDAO();
    private final CalendarSaver calendarSaver = new CalendarSaver();
    private final CategoryUtil categoryUtil = new CategoryUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            CalendarItem item = calendarDAO.getCalendarItem(body.get(ID));
            if (item == null){
                item = new CalendarItem();
            }

            final User user = getUser(req);
            item.setCategory(categoryUtil.getCategory(new JsonObject(body.get(CATEGORY)), user));
            
            boolean useDate = body.getBoolean(USE_DATE);

            if (useDate){
                Timestamp timestamp = body.getTimestamp(DATE);
                final LocalDateTime localDateTime = timestamp.toLocalDateTime();
                final LocalDate localDate = localDateTime.toLocalDate();
                item.setDate(Date.valueOf(localDate));
                boolean useTime = body.getBoolean(USE_TIME);
                if (useTime){
                    final LocalTime localTime = localDateTime.toLocalTime();
                    item.setTime(Time.valueOf(localTime));
                    long length = body.getLong(LENGTH);
                    final LocalDateTime endDateTime = localDateTime.plusMinutes(length);
                    item.setEndDate(Date.valueOf(endDateTime.toLocalDate()));
                    item.setEndTime(Time.valueOf(endDateTime.toLocalTime()));
                } else {
                    item.setTime(null);
                }

            }  else {
                item.setDate(null);
                item.setTime(null);
                item.setEndDate(null);
                item.setEndTime(null);
            }

            Repeat repeat = Repeat.valueOf(body.getString(REPEAT));
            item.setRepeat(repeat);

            calendarSaver.save(item);
        }
        write(resp, SUCCESS_ANSWER);
    }
}
