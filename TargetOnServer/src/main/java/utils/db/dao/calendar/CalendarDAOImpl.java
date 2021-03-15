package utils.db.dao.calendar;

import entity.calendar.CalendarItem;
import entity.user.User;
import utils.db.hibernate.Hibernator;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import static constants.Keys.*;

public class CalendarDAOImpl implements CalendarDAO {

    private final Hibernator hibernator = Hibernator.getInstance();

    @Override
    public List<CalendarItem> getCalendarItems(Date date, User user) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put(TITLE_OWNER, user);
        params.put(DATE, date);
        return hibernator.query(CalendarItem.class, params);
    }

    @Override
    public CalendarItem getCalendarItem(Object id) {
        return hibernator.get(CalendarItem.class, ID, id);
    }

    @Override
    public void saveCalendarItem(CalendarItem item) {
        hibernator.save(item.getHeader());
        hibernator.save(item);
    }

    @Override
    public CalendarItem getCalendarItemByCategory(Object category) {
        return hibernator.get(CalendarItem.class, CATEGORY, category);
    }
}
