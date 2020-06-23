package utils.db.dao.calendar;

import entity.calendar.CalendarItem;

import java.sql.Date;
import java.util.List;

public interface CalendarDAO {
    List<CalendarItem> getCalendarItems(Date date);
    CalendarItem getCalendarItem(Object id);
    void saveCalendarItem(CalendarItem item);
}
