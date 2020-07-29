package utils.db.dao.calendar;

import entity.calendar.CalendarItem;
import entity.user.User;

import java.sql.Date;
import java.util.List;

public interface CalendarDAO {
    List<CalendarItem> getCalendarItems(Date date, User user);
    CalendarItem getCalendarItem(Object id);
    void saveCalendarItem(CalendarItem item);
    CalendarItem getCalendarItemByCategory(Object id);
}
