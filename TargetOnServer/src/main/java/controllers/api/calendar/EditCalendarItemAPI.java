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

            if (body.containKey(DATE)){
                Date date = Date.valueOf(body.getString(DATE));
                item.setDate(date);
                if (body.containKey(TIME)){
                    Time time = Time.valueOf(body.getString(TIME));
                    item.setTime(time);
                    if (body.containKey(LENGTH)){
                        Time length = Time.valueOf(body.getString(LENGTH));
                        LocalDateTime localDateTime = LocalDateTime.of(date.toLocalDate(), time.toLocalTime()).plusMinutes(length.getTime() / 1000/ 60);
                        item.setEndDate(Date.valueOf(localDateTime.toLocalDate()));
                        item.setEndTime(Time.valueOf(localDateTime.toLocalTime()));
                    }
                } else {
                    item.setTime(null);
                }
            } else {
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
