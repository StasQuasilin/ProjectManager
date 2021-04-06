package controllers.api.calendar;

import constants.ApiLinks;
import controllers.api.API;
import entity.calendar.CalendarItem;
import entity.calendar.Repeat;
import entity.calendar.WeekDays;
import entity.finance.category.Header;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.CategoryUtil;
import utils.db.dao.calendar.CalendarDAO;
import utils.db.dao.daoService;
import utils.json.JsonObject;
import utils.savers.CalendarSaver;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

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
            final User user = getUser(req);
            CalendarItem item = calendarDAO.getCalendarItem(body.get(ID));
            if (item == null){
                item = new CalendarItem();
                item.setOwner(user);
            }

            setCategory(item, new JsonObject(body.get(HEADER)), user);

            if (body.containKey(DATE)){
                Date date = Date.valueOf(body.getString(DATE));
                item.setDate(date);
                if (body.containKey(TIME)){
                    Time time = Time.valueOf(body.getString(TIME));
                    item.setTime(time);
                } else {
                    item.setTime(null);
                }
            } else {
                item.setDate(null);
                item.setTime(null);
            }

            Repeat repeat = Repeat.valueOf(body.getString(REPEAT));
            item.setRepeat(repeat);
            if(repeat == Repeat.day){
                parseWeekDays(body.getJsonArray(WEEK_DAYS), item);
            }

            calendarSaver.save(item);
        }
        write(resp, SUCCESS_ANSWER);
    }

    private void parseWeekDays(JSONArray weekDay, CalendarItem item) {
        boolean[] booleans = new boolean[weekDay.size()];
        JsonObject jsonObject;
        String key = "v";
        int i = 0;
        for (Object o : weekDay){
            jsonObject = new JsonObject(o);
            booleans[i++] = jsonObject.getBoolean(key);
        }
        WeekDays weekDays = new WeekDays(booleans);
        final int daysValue = weekDays.getDaysValue();
        if(daysValue == 0){
            item.setRepeat(Repeat.none);
        } else {
            item.setWeekDays(daysValue);
        }
    }

    private void setCategory(CalendarItem item, JsonObject categoryObject, User user) {
        final Header header = categoryUtil.getCategory(categoryObject.getInt(ID), categoryObject.getString(TITLE), user);
        item.setHeader(header);
    }
}
