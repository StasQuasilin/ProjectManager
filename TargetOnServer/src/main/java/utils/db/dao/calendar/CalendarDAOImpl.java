package utils.db.dao.calendar;

import entity.calendar.CalendarItem;
import utils.db.hibernate.Hibernator;

import java.sql.Date;
import java.util.List;
import static constants.Keys.ID;

public class CalendarDAOImpl implements CalendarDAO {

    private final Hibernator hibernator = Hibernator.getInstance();

    @Override
    public List<CalendarItem> getCalendarItems(Date date) {
        return null;
    }

    @Override
    public CalendarItem getCalendarItem(Object id) {
        return hibernator.get(CalendarItem.class, ID, id);
    }

    @Override
    public void saveCalendarItem(CalendarItem item) {
        hibernator.save(item.getCategory());
        hibernator.save(item);
    }
}
